// Fill out your copyright notice in the Description page of Project Settings.

#include "ReaperFunctionsLib.h"

//Foreground Window check, clipboard copy/paste
#include "Runtime/ApplicationCore/Public/HAL/PlatformApplicationMisc.h"

//FGPUDriverInfo GPU 
#include "Runtime/Core/Public/GenericPlatform/GenericPlatformDriver.h"

/*MD5 Hash
#include "Runtime/Core/Public/Misc/SecureHash.h"

#include "Runtime/Engine/Public/VorbisAudioInfo.h"


#include "StaticMeshResources.h"

#include "HeadMountedDisplay.h"

#include "GenericTeamAgentInterface.h"
*/

//For PIE error messages
#include "Runtime/Core/Public/Logging/MessageLog.h"
#define LOCTEXT_NAMESPACE "Fun BP Lib"

//Use MessasgeLog like this: (see GameplayStatics.cpp
/*
#if WITH_EDITOR
		FMessageLog("PIE").Error(FText::Format(LOCTEXT("SpawnObjectWrongClass", "SpawnObject wrong class: {0}'"), FText::FromString(GetNameSafe(*ObjectClass))));
#endif // WITH_EDITOR
*/

// To be able to perform regex operatins on level stream info package name
#if WITH_EDITOR
#include "Runtime/Core/Public/Internationalization/Regex.h"
#endif

//~~~ Image Wrapper ~~~
#include "ImageUtils.h"
#include "IImageWrapper.h"
#include "IImageWrapperModule.h"
//~~~ Image Wrapper ~~~

#include "IXRTrackingSystem.h"

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//									Saxon Rah Random Nodes
// Chrono and Random

//Order Matters, 
//		has to be after PhysX includes to avoid isfinite name definition issues
#include <chrono>
#include <random>

#include <string>

/*
	~~~ Rama File Operations CopyRight ~~~

	If you use any of my file operation code below,
	please credit me somewhere appropriate as "Rama"
*/
template <class FunctorType>
class PlatformFileFunctor : public IPlatformFile::FDirectoryVisitor	//GenericPlatformFile.h
{
public:

	virtual bool Visit(const TCHAR* FilenameOrDirectory, bool bIsDirectory) override
	{
		return Functor(FilenameOrDirectory, bIsDirectory);
	}

	PlatformFileFunctor(FunctorType&& FunctorInstance)
		: Functor(MoveTemp(FunctorInstance))
	{
	}

private:
	FunctorType Functor;
};

template <class Functor>
PlatformFileFunctor<Functor> MakeDirectoryVisitor(Functor&& FunctorInstance)
{
	return PlatformFileFunctor<Functor>(MoveTemp(FunctorInstance));
}

static FDateTime GetFileTimeStamp(const FString& File)
{
	return FPlatformFileManager::Get().GetPlatformFile().GetTimeStamp(*File);
}
static void SetTimeStamp(const FString& File, const FDateTime& TimeStamp)
{
	FPlatformFileManager::Get().GetPlatformFile().SetTimeStamp(*File, TimeStamp);
}

//Radial Result Struct
struct FFileStampSort
{
	FString* FileName;
	FDateTime FileStamp;

	FFileStampSort(FString* IN_Name, FDateTime Stamp)
		: FileName(IN_Name)
		, FileStamp(Stamp)
	{}
};

//For Array::Sort()
FORCEINLINE bool operator< (const FFileStampSort& Left, const FFileStampSort& Right)
{
	return Left.FileStamp < Right.FileStamp;
}

//Written by Rama, please credit me if you use this code elsewhere
static bool GetFiles(const FString& FullPathOfBaseDir, TArray<FString>& FilenamesOut, bool Recursive = false, const FString& FilterByExtension = "")
{
	//Format File Extension, remove the "." if present
	const FString FileExt = FilterByExtension.Replace(TEXT("."), TEXT("")).ToLower();

	FString Str;
	auto FilenamesVisitor = MakeDirectoryVisitor(
		[&](const TCHAR* FilenameOrDirectory, bool bIsDirectory)
		{
			//Files
			if (!bIsDirectory)
			{
				//Filter by Extension
				if (FileExt != "")
				{
					Str = FPaths::GetCleanFilename(FilenameOrDirectory);

					//Filter by Extension
					if (FPaths::GetExtension(Str).ToLower() == FileExt)
					{
						if (Recursive)
						{
							FilenamesOut.Push(FilenameOrDirectory); //need whole path for recursive
						}
						else
						{
							FilenamesOut.Push(Str);
						}
					}
				}

				//Include All Filenames!
				else
				{
					//Just the Directory
					Str = FPaths::GetCleanFilename(FilenameOrDirectory);

					if (Recursive)
					{
						FilenamesOut.Push(FilenameOrDirectory); //need whole path for recursive
					}
					else
					{
						FilenamesOut.Push(Str);
					}
				}
			}
			return true;
		}
	);
	if (Recursive)
	{
		return FPlatformFileManager::Get().GetPlatformFile().IterateDirectoryRecursively(*FullPathOfBaseDir, FilenamesVisitor);
	}
	else
	{
		return FPlatformFileManager::Get().GetPlatformFile().IterateDirectory(*FullPathOfBaseDir, FilenamesVisitor);
	}
}

static bool FileExists(const FString& File)
{
	return FPlatformFileManager::Get().GetPlatformFile().FileExists(*File);
}
static bool FolderExists(const FString& Dir)
{
	return FPlatformFileManager::Get().GetPlatformFile().DirectoryExists(*Dir);
}
static bool RenameFile(const FString& Dest, const FString& Source)
{
	//Make sure file modification time gets updated!
	SetTimeStamp(Source, FDateTime::Now());

	return FPlatformFileManager::Get().GetPlatformFile().MoveFile(*Dest, *Source);
}

//Create Directory, Creating Entire Structure as Necessary
//		so if JoyLevels and Folder1 do not exist in JoyLevels/Folder1/Folder2
//			they will be created so that Folder2 can be created!

//This is my solution for fact that trying to create a directory fails 
//		if its super directories do not exist
static bool VCreateDirectory(FString FolderToMake) //not const so split can be used, and does not damage input
{
	if (FolderExists(FolderToMake))
	{
		return true;
	}

	// Normalize all / and \ to TEXT("/") and remove any trailing TEXT("/") if the character before that is not a TEXT("/") or a colon
	FPaths::NormalizeDirectoryName(FolderToMake);

	//Normalize removes the last "/", but is needed by algorithm
	//  Guarantees While loop will end in a timely fashion.
	FolderToMake += "/";

	FString Base;
	FString Left;
	FString Remaining;

	//Split off the Root
	FolderToMake.Split(TEXT("/"), &Base, &Remaining);
	Base += "/"; //add root text and Split Text to Base

	while (Remaining != "")
	{
		Remaining.Split(TEXT("/"), &Left, &Remaining);

		//Add to the Base
		Base += Left + FString("/"); //add left and split text to Base

		//Create Incremental Directory Structure!
		if (!FPlatformFileManager::Get().GetPlatformFile().CreateDirectory(*Base) &&
			!FPlatformFileManager::Get().GetPlatformFile().DirectoryExists(*Base))
		{
			return false;
			//~~~~~
		}
	}

	return true;
}
/*
	~~~ Rama File Operations CopyRight ~~~

	If you use any of my file operation code above,
	please credit me somewhere appropriate as "Rama"
*/

static int32 GetChildBones(const FReferenceSkeleton& ReferenceSkeleton, int32 ParentBoneIndex, TArray<int32>& Children)
{
	Children.Empty();

	const int32 NumBones = ReferenceSkeleton.GetNum();
	for (int32 ChildIndex = ParentBoneIndex + 1; ChildIndex < NumBones; ChildIndex++)
	{
		if (ParentBoneIndex == ReferenceSkeleton.GetParentIndex(ChildIndex))
		{
			Children.Add(ChildIndex);
		}
	}

	return Children.Num();
}
static void GetChildBoneNames_Recursive(USkeletalMeshComponent* SkeletalMeshComp, int32 ParentBoneIndex, TArray<FName>& ChildBoneNames)
{
	TArray<int32> BoneIndicies;
	GetChildBones(SkeletalMeshComp->SkeletalMesh->RefSkeleton, ParentBoneIndex, BoneIndicies);

	if (BoneIndicies.Num() < 1)
	{
		//Stops the recursive skeleton search
		return;
	}

	for (const int32& BoneIndex : BoneIndicies)
	{
		FName ChildBoneName = SkeletalMeshComp->GetBoneName(BoneIndex);
		ChildBoneNames.Add(ChildBoneName);

		//Recursion
		GetChildBoneNames_Recursive(SkeletalMeshComp, BoneIndex, ChildBoneNames);
	}
}




//~~~~~~
// File IO
//~~~~~~ 
bool UReaperFunctionsLib::JoyFileIO_GetFiles(TArray<FString>& Files, FString RootFolderFullPath, FString Ext)
{
	if (RootFolderFullPath.Len() < 1) return false;

	FPaths::NormalizeDirectoryName(RootFolderFullPath);

	IFileManager& FileManager = IFileManager::Get();

	if (!Ext.Contains(TEXT("*")))
	{
		if (Ext == "")
		{
			Ext = "*.*";
		}
		else
		{
			Ext = (Ext.Left(1) == ".") ? "*" + Ext : "*." + Ext;
		}
	}

	FString FinalPath = RootFolderFullPath + "/" + Ext;

	FileManager.FindFiles(Files, *FinalPath, true, false);
	return true;
}
bool UReaperFunctionsLib::JoyFileIO_GetFilesInRootAndAllSubFolders(TArray<FString>& Files, FString RootFolderFullPath, FString Ext)
{
	if (RootFolderFullPath.Len() < 1) return false;

	FPaths::NormalizeDirectoryName(RootFolderFullPath);

	IFileManager& FileManager = IFileManager::Get();

	if (!Ext.Contains(TEXT("*")))
	{
		if (Ext == "")
		{
			Ext = "*.*";
		}
		else
		{
			Ext = (Ext.Left(1) == ".") ? "*" + Ext : "*." + Ext;
		}
	}

	FileManager.FindFilesRecursive(Files, *RootFolderFullPath, *Ext, true, false);
	return true;
}

bool UReaperFunctionsLib::ScreenShots_Rename_Move_Most_Recent(
	FString& OriginalFileName,
	FString NewName,
	FString NewAbsoluteFolderPath,
	bool HighResolution
) {
	OriginalFileName = "None";

	//File Name given?
	if (NewName.Len() <= 0) return false;

	//Normalize
	FPaths::NormalizeDirectoryName(NewAbsoluteFolderPath);

	//Ensure target directory exists, 
	//		_or can be created!_ <3 Rama
	if (!VCreateDirectory(NewAbsoluteFolderPath)) return false;

	FString ScreenShotsDir = VictoryPaths__ScreenShotsDir();

	//Find all screenshots
	TArray<FString> Files;									//false = not recursive, not expecting subdirectories
	bool Success = GetFiles(ScreenShotsDir, Files, false);

	if (!Success)
	{
		return false;
	}

	//Filter
	TArray<FString> ToRemove; //16 bytes each, more stable than ptrs though since RemoveSwap is involved
	for (FString& Each : Files)
	{
		if (HighResolution)
		{
			//remove those that dont have it
			if (Each.Left(4) != "High")
			{
				ToRemove.Add(Each);
			}
		}
		else
		{
			//Remove those that have it!
			if (Each.Left(4) == "High")
			{
				ToRemove.Add(Each);
			}
		}
	}

	//Remove!
	for (FString& Each : ToRemove)
	{
		Files.RemoveSwap(Each); //Fast remove! Does not preserve order
	}

	//No files?
	if (Files.Num() < 1)
	{
		return false;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Rama's Sort Files by Time Stamp Feature
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Sort the file names by time stamp
	//  This is my custom c++ struct to do this,
	//  	combined with my operator< and UE4's
	//			TArray::Sort() function!
	TArray<FFileStampSort> FileSort;
	for (FString& Each : Files)
	{
		FileSort.Add(FFileStampSort(&Each, GetFileTimeStamp(Each)));

	}

	//Sort all the file names by their Time Stamp!
	FileSort.Sort();

	//Biggest value = last entry
	OriginalFileName = *FileSort.Last().FileName;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~


	//Generate new Full File Path!
	FString NewFullFilePath = NewAbsoluteFolderPath + "/" + NewName + ".png";

	//Move File!
	return RenameFile(NewFullFilePath, ScreenShotsDir + "/" + OriginalFileName);
}


void UReaperFunctionsLib::SaveGameObject_GetAllSaveSlotFileNames(TArray<FString>& FileNames)
{
	FileNames.Empty();
	FString Path = VictoryPaths__SavedDir() + "SaveGames";
	GetFiles(Path, FileNames); //see top of this file, my own file IO code - Rama
}

//~~~ Victory Paths ~~~

FString UReaperFunctionsLib::VictoryPaths__Win64Dir_BinaryLocation()
{
	return FString(FPlatformProcess::BaseDir());
}

FString UReaperFunctionsLib::VictoryPaths__WindowsNoEditorDir()
{
	return FPaths::ConvertRelativePathToFull(FPaths::RootDir());
}

FString UReaperFunctionsLib::VictoryPaths__GameRootDirectory()
{
	return FPaths::ConvertRelativePathToFull(FPaths::ProjectDir());
}

FString UReaperFunctionsLib::VictoryPaths__SavedDir()
{
	return FPaths::ConvertRelativePathToFull(FPaths::ProjectSavedDir());
}
FString UReaperFunctionsLib::VictoryPaths__ConfigDir()
{
	return FPaths::ConvertRelativePathToFull(FPaths::ProjectConfigDir());
}

FString UReaperFunctionsLib::VictoryPaths__ScreenShotsDir()
{
	return FPaths::ConvertRelativePathToFull(FPaths::ScreenShotDir());
}

FString UReaperFunctionsLib::VictoryPaths__LogsDir()
{
	return FPaths::ConvertRelativePathToFull(FPaths::ProjectLogDir());
}


//~~~~~~~~~~~~~~~~~

void UReaperFunctionsLib::VictoryIntPlusEquals(UPARAM(ref) int32& Int, int32 Add, int32& IntOut)
{
	Int += Add;
	IntOut = Int;
}
void UReaperFunctionsLib::VictoryIntMinusEquals(UPARAM(ref) int32& Int, int32 Sub, int32& IntOut)
{
	Int -= Sub;
	IntOut = Int;
}

void UReaperFunctionsLib::VictoryFloatPlusEquals(UPARAM(ref) float& Float, float Add, float& FloatOut)
{
	Float += Add;
	FloatOut = Float;
}
void UReaperFunctionsLib::VictoryFloatMinusEquals(UPARAM(ref) float& Float, float Sub, float& FloatOut)
{
	Float -= Sub;
	FloatOut = Float;
}

void UReaperFunctionsLib::VictorySortIntArray(UPARAM(ref) TArray<int32>& IntArray, TArray<int32>& IntArrayRef)
{
	IntArray.Sort();
	IntArrayRef = IntArray;
}
void UReaperFunctionsLib::VictorySortFloatArray(UPARAM(ref) TArray<float>& FloatArray, TArray<float>& FloatArrayRef)
{
	FloatArray.Sort();
	FloatArrayRef = FloatArray;
}

//String Back To Type
void UReaperFunctionsLib::Conversions__StringToColor(const FString& String, FLinearColor& ConvertedColor, bool& IsValid)
{
	IsValid = ConvertedColor.InitFromString(String);
}
void UReaperFunctionsLib::Conversions__ColorToString(const FLinearColor& Color, FString& ColorAsString)
{
	ColorAsString = Color.ToString();
}

//String Back To Type

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

UObject* UReaperFunctionsLib::CreateObject(UObject* WorldContextObject, UClass* TheObjectClass)
{
	//See deprecation warning
	//	Deprecation warning makes it no longer appear in context menu as a new node to add
	return nullptr;
	/*
	if(!TheObjectClass) return NULL;
	//~~~~~~~~~~~~~~~~~

	//using a context object to get the world!
	UWorld* const World = GEngine->GetWorldFromContextObject(WorldContextObject, EGetWorldErrorMode::ReturnNull);
	if(!World) return NULL;
	//~~~~~~~~~~~

	//Need to submit pull request for custom name and custom class both
	return NewObject<UObject>(World,TheObjectClass);
	*/
}
UPrimitiveComponent* UReaperFunctionsLib::CreatePrimitiveComponent(
	UObject* WorldContextObject,
	TSubclassOf<UPrimitiveComponent> CompClass,
	FName Name,
	FVector Location,
	FRotator Rotation
) {
	if (!CompClass) return NULL;
	//~~~~~~~~~~~~~~~~~

	//using a context object to get the world!
	UWorld* const World = GEngine->GetWorldFromContextObject(WorldContextObject, EGetWorldErrorMode::ReturnNull);
	if (!World) return NULL;
	//~~~~~~~~~~~

	UPrimitiveComponent* NewComp = NewObject<UPrimitiveComponent>(World, Name);
	if (!NewComp) return NULL;
	//~~~~~~~~~~~~~

	NewComp->SetWorldLocation(Location);
	NewComp->SetWorldRotation(Rotation);
	NewComp->RegisterComponentWithWorld(World);

	return NewComp;
}


bool UReaperFunctionsLib::LoadStringArrayFromFile(TArray<FString>& StringArray, int32& ArraySize, FString FullFilePath, bool ExcludeEmptyLines)
{
	ArraySize = 0;

	if (FullFilePath == "" || FullFilePath == " ") return false;

	//Empty any previous contents!
	StringArray.Empty();

	TArray<FString> FileArray;

	if (!FFileHelper::LoadANSITextFileToStrings(*FullFilePath, NULL, FileArray))
	{
		return false;
	}

	if (ExcludeEmptyLines)
	{
		for (const FString& Each : FileArray)
		{
			if (Each == "") continue;
			//~~~~~~~~~~~~~

			//check for any non whitespace
			bool FoundNonWhiteSpace = false;
			for (int32 v = 0; v < Each.Len(); v++)
			{
				if (Each[v] != ' ' && Each[v] != '\n')
				{
					FoundNonWhiteSpace = true;
					break;
				}
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			}

			if (FoundNonWhiteSpace)
			{
				StringArray.Add(Each);
			}
		}
	}
	else
	{
		StringArray.Append(FileArray);
	}

	ArraySize = StringArray.Num();
	return true;
}

void UReaperFunctionsLib::OperatingSystem__GetCurrentPlatform(
	bool& Windows_, 		//some weird bug of making it all caps engine-side
	bool& Mac,
	bool& Linux,
	bool& iOS,
	bool& Android,
	bool& Android_ARM,
	bool& Android_Vulkan,
	bool& PS4,
	bool& XBoxOne,
	bool& HTML5,
	bool& Apple
) {
	//#define's in UE4 source code
	Windows_ = PLATFORM_WINDOWS;
	Mac = PLATFORM_MAC;
	Linux = PLATFORM_LINUX;

	PS4 = PLATFORM_PS4;
	XBoxOne = PLATFORM_XBOXONE;

	iOS = PLATFORM_IOS;
	Android = PLATFORM_ANDROID;
	Android_ARM = PLATFORM_ANDROID_ARM;
	Android_Vulkan = PLATFORM_ANDROID_VULKAN;
	HTML5 = false;//PLATFORM_HTML5;

	Apple = PLATFORM_APPLE;
}

void UReaperFunctionsLib::MaxOfFloatArray(const TArray<float>& FloatArray, int32& IndexOfMaxValue, float& MaxValue)
{
	MaxValue = UReaperFunctionsLib::Max(FloatArray, &IndexOfMaxValue);
}

void UReaperFunctionsLib::MaxOfIntArray(const TArray<int32>& IntArray, int32& IndexOfMaxValue, int32& MaxValue)
{
	MaxValue = UReaperFunctionsLib::Max(IntArray, &IndexOfMaxValue);
}

void UReaperFunctionsLib::MinOfFloatArray(const TArray<float>& FloatArray, int32& IndexOfMinValue, float& MinValue)
{
	MinValue = UReaperFunctionsLib::Min(FloatArray, &IndexOfMinValue);
}

void UReaperFunctionsLib::MinOfIntArray(const TArray<int32>& IntArray, int32& IndexOfMinValue, int32& MinValue)
{
	MinValue = UReaperFunctionsLib::Min(IntArray, &IndexOfMinValue);
}

bool UReaperFunctionsLib::HasSubstring(const FString& SearchIn, const FString& Substring, ESearchCase::Type SearchCase, ESearchDir::Type SearchDir)
{
	return SearchIn.Contains(Substring, SearchCase, SearchDir);
}

FString UReaperFunctionsLib::String__CombineStrings(FString StringFirst, FString StringSecond, FString Separator, FString StringFirstLabel, FString StringSecondLabel)
{
	return StringFirstLabel + StringFirst + Separator + StringSecondLabel + StringSecond;
}
FString UReaperFunctionsLib::String__CombineStrings_Multi(FString A, FString B)
{
	return A + " " + B;
}

//SMA Version

//~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~
//~~~~~~~~~~~~~~~~~~~~~~~~~~~
//			  Contributed by Others

	/**
	* Contributed by: SaxonRah
	* Better random numbers. Seeded with a random device. if the random device's entropy is 0; defaults to current time for seed.
	* can override with seed functions;
	*/
	//----------------------------------------------------------------------------------------------BeginRANDOM
std::random_device rd;
unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();

std::mt19937 rand_MT;
std::default_random_engine rand_DRE;

/** Construct a random device and set seed for engines dependent on entropy */
void UReaperFunctionsLib::constructRand()
{
	seed = std::chrono::system_clock::now().time_since_epoch().count();

	if (rd.entropy() == 0)
	{
		seedRand(seed);
	}
	else {
		seedRand(rd());
	}
}
/** Set seed for Rand */
void UReaperFunctionsLib::seedRand(int32 _seed)
{
	seed = _seed;
}

/** Set seed with time for Rand */
void UReaperFunctionsLib::seedRandWithTime()
{
	seed = std::chrono::system_clock::now().time_since_epoch().count();
}

/** Set seed with entropy for Rand */
void UReaperFunctionsLib::seedRandWithEntropy()
{
	seedRand(rd());
}

/** Random Bool - Bernoulli distribution */
bool UReaperFunctionsLib::RandBool_Bernoulli(float fBias)
{
	std::bernoulli_distribution dis(fBias);
	return dis(rand_DRE);
}

/** Random Integer - Uniform distribution */
int32 UReaperFunctionsLib::RandInt_uniDis()
{
	std::uniform_int_distribution<int32> dis(0, 1);
	return dis(rand_DRE);
}
/** Random Integer - Uniform distribution */
int32 UReaperFunctionsLib::RandInt_MINMAX_uniDis(int32 iMin, int32 iMax)
{
	std::uniform_int_distribution<int32> dis(iMin, iMax);
	return dis(rand_DRE);
}

/** Random Float - Zero to One Uniform distribution */
float UReaperFunctionsLib::RandFloat_uniDis()
{
	std::uniform_real_distribution<float> dis(0, 1);
	return dis(rand_DRE);
}
/** Random Float - MIN to MAX Uniform distribution */
float UReaperFunctionsLib::RandFloat_MINMAX_uniDis(float iMin, float iMax)
{
	std::uniform_real_distribution<float> dis(iMin, iMax);
	return dis(rand_DRE);
}

/** Random Bool - Bernoulli distribution  -  Mersenne Twister */
bool UReaperFunctionsLib::RandBool_Bernoulli_MT(float fBias)
{
	std::bernoulli_distribution dis(fBias);
	return dis(rand_MT);
}

/** Random Integer - Uniform distribution  -  Mersenne Twister */
int32 UReaperFunctionsLib::RandInt_uniDis_MT()
{
	std::uniform_int_distribution<int32> dis(0, 1);
	return dis(rand_MT);
}
/** Random Integer - Uniform distribution  -  Mersenne Twister */
int32 UReaperFunctionsLib::RandInt_MINMAX_uniDis_MT(int32 iMin, int32 iMax)
{
	std::uniform_int_distribution<int32> dis(iMin, iMax);
	return dis(rand_MT);
}

/** Random Float - Zero to One Uniform distribution  -  Mersenne Twister */
float UReaperFunctionsLib::RandFloat_uniDis_MT()
{
	std::uniform_real_distribution<float> dis(0, 1);
	return dis(rand_MT);
}
/** Random Float - MIN to MAX Uniform distribution  -  Mersenne Twister */
float UReaperFunctionsLib::RandFloat_MINMAX_uniDis_MT(float iMin, float iMax)
{
	std::uniform_real_distribution<float> dis(iMin, iMax);
	return dis(rand_MT);
}
//----------------------------------------------------------------------------------------------ENDRANDOM


//this is how you can make cpp only internal functions!
static EImageFormat GetJoyImageFormat(EJoyImgFormats JoyFormat)
{
	/*
	ImageWrapper.h
	namespace EImageFormat
	{

	Enumerates the types of image formats this class can handle

	enum Type
	{
		//Portable Network Graphics
		PNG,

		//Joint Photographic Experts Group
		JPEG,

		//Single channel jpeg
		GrayscaleJPEG,

		//Windows Bitmap
		BMP,

		//Windows Icon resource
		ICO,

		//OpenEXR (HDR) image file format
		EXR,

		//Mac icon
		ICNS
	};
};
	*/
	switch (JoyFormat)
	{
	case EJoyImgFormats::JPG: return EImageFormat::JPEG;
	case EJoyImgFormats::PNG: return EImageFormat::PNG;
	case EJoyImgFormats::BMP: return EImageFormat::BMP;
	case EJoyImgFormats::ICO: return EImageFormat::ICO;
	case EJoyImgFormats::EXR: return EImageFormat::EXR;
	case EJoyImgFormats::ICNS: return EImageFormat::ICNS;
	}
	return EImageFormat::JPEG;
}

static FString GetJoyImageExtension(EJoyImgFormats JoyFormat)
{
	switch (JoyFormat)
	{
	case EJoyImgFormats::JPG: return ".jpg";
	case EJoyImgFormats::PNG: return ".png";
	case EJoyImgFormats::BMP: return ".bmp";
	case EJoyImgFormats::ICO: return ".ico";
	case EJoyImgFormats::EXR: return ".exr";
	case EJoyImgFormats::ICNS: return ".icns";
	}
	return ".png";
}
UTexture2D* UReaperFunctionsLib::Victory_LoadTexture2D_FromFile(const FString& FullFilePath, EJoyImgFormats ImageFormat, bool& IsValid, int32& Width, int32& Height)
{


	IsValid = false;
	UTexture2D* LoadedT2D = NULL;

	IImageWrapperModule& ImageWrapperModule = FModuleManager::LoadModuleChecked<IImageWrapperModule>(FName("ImageWrapper"));
	TSharedPtr<IImageWrapper> ImageWrapper = ImageWrapperModule.CreateImageWrapper(GetJoyImageFormat(ImageFormat));

	//Load From File
	TArray<uint8> RawFileData;
	if (!FFileHelper::LoadFileToArray(RawFileData, *FullFilePath)) return NULL;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//Create T2D!
	if (ImageWrapper.IsValid() && ImageWrapper->SetCompressed(RawFileData.GetData(), RawFileData.Num()))
	{
		const TArray<uint8>* UncompressedBGRA = NULL;
		if (ImageWrapper->GetRaw(ERGBFormat::BGRA, 8, UncompressedBGRA))
		{
			LoadedT2D = UTexture2D::CreateTransient(ImageWrapper->GetWidth(), ImageWrapper->GetHeight(), PF_B8G8R8A8);

			//Valid?
			if (!LoadedT2D) return NULL;
			//~~~~~~~~~~~~~~

			//Out!
			Width = ImageWrapper->GetWidth();
			Height = ImageWrapper->GetHeight();

			//Copy!
			void* TextureData = LoadedT2D->PlatformData->Mips[0].BulkData.Lock(LOCK_READ_WRITE);
			FMemory::Memcpy(TextureData, UncompressedBGRA->GetData(), UncompressedBGRA->Num());
			LoadedT2D->PlatformData->Mips[0].BulkData.Unlock();

			//Update!
			LoadedT2D->UpdateResource();
		}
	}

	// Success!
	IsValid = true;
	return LoadedT2D;
}
UTexture2D* UReaperFunctionsLib::Victory_LoadTexture2D_FromFile_Pixels(const FString& FullFilePath, EJoyImgFormats ImageFormat, bool& IsValid, int32& Width, int32& Height, TArray<FLinearColor>& OutPixels)
{
	//Clear any previous data
	OutPixels.Empty();

	IsValid = false;
	UTexture2D* LoadedT2D = NULL;

	IImageWrapperModule& ImageWrapperModule = FModuleManager::LoadModuleChecked<IImageWrapperModule>(FName("ImageWrapper"));
	TSharedPtr<IImageWrapper> ImageWrapper = ImageWrapperModule.CreateImageWrapper(GetJoyImageFormat(ImageFormat));

	//Load From File
	TArray<uint8> RawFileData;
	if (!FFileHelper::LoadFileToArray(RawFileData, *FullFilePath)) return NULL;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//Create T2D!
	if (ImageWrapper.IsValid() && ImageWrapper->SetCompressed(RawFileData.GetData(), RawFileData.Num()))
	{
		const TArray<uint8>* UncompressedRGBA = NULL;
		if (ImageWrapper->GetRaw(ERGBFormat::RGBA, 8, UncompressedRGBA))
		{
			LoadedT2D = UTexture2D::CreateTransient(ImageWrapper->GetWidth(), ImageWrapper->GetHeight(), PF_R8G8B8A8);

			//Valid?
			if (!LoadedT2D) return NULL;
			//~~~~~~~~~~~~~~

			//Out!
			Width = ImageWrapper->GetWidth();
			Height = ImageWrapper->GetHeight();

			const TArray<uint8>& ByteArray = *UncompressedRGBA;
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			for (int32 v = 0; v < ByteArray.Num(); v += 4)
			{
				if (!ByteArray.IsValidIndex(v + 3))
				{
					break;
				}

				OutPixels.Add(
					FLinearColor(
						ByteArray[v],		//R
						ByteArray[v + 1],		//G
						ByteArray[v + 2],		//B
						ByteArray[v + 3] 		//A
					)
				);
			}
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			//Copy!
			void* TextureData = LoadedT2D->PlatformData->Mips[0].BulkData.Lock(LOCK_READ_WRITE);
			FMemory::Memcpy(TextureData, UncompressedRGBA->GetData(), UncompressedRGBA->Num());
			LoadedT2D->PlatformData->Mips[0].BulkData.Unlock();

			//Update!
			LoadedT2D->UpdateResource();
		}
	}

	// Success!
	IsValid = true;
	return LoadedT2D;

}
bool UReaperFunctionsLib::Victory_Get_Pixel(const TArray<FLinearColor>& Pixels, int32 ImageHeight, int32 x, int32 y, FLinearColor& FoundColor)
{
	int32 Index = y * ImageHeight + x;
	if (!Pixels.IsValidIndex(Index))
	{
		return false;
	}

	FoundColor = Pixels[Index];
	return true;
}


bool UReaperFunctionsLib::Victory_GetPixelFromT2D(UTexture2D* T2D, int32 X, int32 Y, FLinearColor& PixelColor)
{
	if (!T2D)
	{
		return false;
	}

	if (X <= -1 || Y <= -1)
	{
		return false;
	}

	T2D->SRGB = false;
	T2D->CompressionSettings = TC_VectorDisplacementmap;

	//Update settings
	T2D->UpdateResource();

	FTexture2DMipMap& MipsMap = T2D->PlatformData->Mips[0];
	int32 TextureWidth = MipsMap.SizeX;
	int32 TextureHeight = MipsMap.SizeY;

	FByteBulkData* RawImageData = &MipsMap.BulkData;

	if (!RawImageData)
	{
		return false;
	}

	FColor* RawColorArray = static_cast<FColor*>(RawImageData->Lock(LOCK_READ_ONLY));

	//Safety check!
	if (X >= TextureWidth || Y >= TextureHeight)
	{
		return false;
	}

	//Get!, converting FColor to FLinearColor 
	PixelColor = RawColorArray[Y * TextureWidth + X];

	RawImageData->Unlock();
	return true;
}
bool UReaperFunctionsLib::Victory_GetPixelsArrayFromT2DDynamic(UTexture2DDynamic* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray)
{
	if (!T2D)
	{
		return false;
	}

	//To prevent overflow in BP if used in a loop
	PixelArray.Empty();

	//~~~~~~~~~~~~~~~~~~~~~~
	// Modifying original here
	T2D->SRGB = false;
	T2D->CompressionSettings = TC_VectorDisplacementmap;

	//Update settings
	T2D->UpdateResource();
	//~~~~~~~~~~~~~~~~~~~~~~

	//Confused, DDC / platform data is invalid for dynamic, how to get its byte data?
	//FTextureResource from UTexture base class?
	return false;

	/*
	FTexturePlatformData** PtrPtr = T2D->GetRunningPlatformData();
	if(!PtrPtr) return false;
	FTexturePlatformData* Ptr = *PtrPtr;
	if(!Ptr) return false;

	FTexture2DMipMap& MyMipMap 	= Ptr->Mips[0];
	TextureWidth = MyMipMap.SizeX;
	TextureHeight = MyMipMap.SizeY;

	FByteBulkData* RawImageData 	= &MyMipMap.BulkData;

	if(!RawImageData)
	{
		return false;
	}

	FColor* RawColorArray = static_cast<FColor*>(RawImageData->Lock(LOCK_READ_ONLY));

	UE_LOG(LogTemp,Warning,TEXT("Victory Plugin, Get Pixels, tex width for mip %d"), TextureWidth);
	UE_LOG(LogTemp,Warning,TEXT("Victory Plugin, Get Pixels, tex width from T2D ptr %d"), T2D->GetSurfaceWidth());

	for(int32 x = 0; x < TextureWidth; x++)
	{
		for(int32 y = 0; y < TextureHeight; y++)
		{
			PixelArray.Add(RawColorArray[x * TextureWidth + y]);
		}
	}

	RawImageData->Unlock();
	*/

	return true;
}

bool UReaperFunctionsLib::Victory_GetPixelsArrayFromT2D(UTexture2D* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray)
{
	if (!T2D)
	{
		return false;
	}

	//To prevent overflow in BP if used in a loop
	PixelArray.Empty();

	T2D->SRGB = false;
	T2D->CompressionSettings = TC_VectorDisplacementmap;

	//Update settings
	T2D->UpdateResource();

	FTexture2DMipMap& MyMipMap = T2D->PlatformData->Mips[0];
	TextureWidth = MyMipMap.SizeX;
	TextureHeight = MyMipMap.SizeY;

	FByteBulkData* RawImageData = &MyMipMap.BulkData;

	if (!RawImageData)
	{
		return false;
	}

	FColor* RawColorArray = static_cast<FColor*>(RawImageData->Lock(LOCK_READ_ONLY));

	for (int32 x = 0; x < TextureWidth; x++)
	{
		for (int32 y = 0; y < TextureHeight; y++)
		{
			PixelArray.Add(RawColorArray[x * TextureWidth + y]);
		}
	}

	RawImageData->Unlock();
	return true;
}


//~~~ Kris ~~~

static TSharedPtr<IImageWrapper> GetImageWrapperByExtention(const FString InImagePath)
{
	IImageWrapperModule& ImageWrapperModule = FModuleManager::LoadModuleChecked<IImageWrapperModule>(FName("ImageWrapper"));
	if (InImagePath.EndsWith(".png"))
	{
		return ImageWrapperModule.CreateImageWrapper(EImageFormat::PNG);
	}
	else if (InImagePath.EndsWith(".jpg") || InImagePath.EndsWith(".jpeg"))
	{
		return ImageWrapperModule.CreateImageWrapper(EImageFormat::JPEG);
	}
	else if (InImagePath.EndsWith(".bmp"))
	{
		return ImageWrapperModule.CreateImageWrapper(EImageFormat::BMP);
	}
	else if (InImagePath.EndsWith(".ico"))
	{
		return ImageWrapperModule.CreateImageWrapper(EImageFormat::ICO);
	}
	else if (InImagePath.EndsWith(".exr"))
	{
		return ImageWrapperModule.CreateImageWrapper(EImageFormat::EXR);
	}
	else if (InImagePath.EndsWith(".icns"))
	{
		return ImageWrapperModule.CreateImageWrapper(EImageFormat::ICNS);
	}

	return nullptr;
}

bool UReaperFunctionsLib::CaptureComponent2D_SaveImage(class USceneCaptureComponent2D* Target, const FString ImagePath, const FLinearColor ClearColour)
{
	// Bad scene capture component! No render target! Stay! Stay! Ok, feed!... wait, where was I?
	if ((Target == nullptr) || (Target->TextureTarget == nullptr))
	{
		return false;
	}

	FRenderTarget* RenderTarget = Target->TextureTarget->GameThread_GetRenderTargetResource();
	if (RenderTarget == nullptr)
	{
		return false;
	}

	TArray<FColor> RawPixels;

	// Format not supported - use PF_B8G8R8A8.
	if (Target->TextureTarget->GetFormat() != PF_B8G8R8A8)
	{
		// TRACEWARN("Format not supported - use PF_B8G8R8A8.");
		return false;
	}

	if (!RenderTarget->ReadPixels(RawPixels))
	{
		return false;
	}

	// Convert to FColor.
	FColor ClearFColour = ClearColour.ToFColor(false); // FIXME - want sRGB or not?

	for (auto& Pixel : RawPixels)
	{
		// Switch Red/Blue changes.
		const uint8 PR = Pixel.R;
		const uint8 PB = Pixel.B;
		Pixel.R = PB;
		Pixel.B = PR;

		// Set alpha based on RGB values of ClearColour.
		Pixel.A = ((Pixel.R == ClearFColour.R) && (Pixel.G == ClearFColour.G) && (Pixel.B == ClearFColour.B)) ? 0 : 255;
	}

	TSharedPtr<IImageWrapper> ImageWrapper = GetImageWrapperByExtention(ImagePath);

	const int32 Width = Target->TextureTarget->SizeX;
	const int32 Height = Target->TextureTarget->SizeY;

	if (ImageWrapper.IsValid() && ImageWrapper->SetRaw(&RawPixels[0], RawPixels.Num() * sizeof(FColor), Width, Height, ERGBFormat::RGBA, 8))
	{
		FFileHelper::SaveArrayToFile(ImageWrapper->GetCompressed(), *ImagePath);
		return true;
	}

	return false;
}

bool UReaperFunctionsLib::Capture2D_SaveImage(class ASceneCapture2D* Target, const FString ImagePath, const FLinearColor ClearColour)
{
	return (Target) ? CaptureComponent2D_SaveImage(Target->GetCaptureComponent2D(), ImagePath, ClearColour) : false;
}

UTexture2D* UReaperFunctionsLib::LoadTexture2D_FromFileByExtension(const FString& ImagePath, bool& IsValid, int32& OutWidth, int32& OutHeight)
{
	UTexture2D* Texture = nullptr;
	IsValid = false;

	// To avoid log spam, make sure it exists before doing anything else.
	if (!FPlatformFileManager::Get().GetPlatformFile().FileExists(*ImagePath))
	{
		return nullptr;
	}

	TArray<uint8> CompressedData;
	if (!FFileHelper::LoadFileToArray(CompressedData, *ImagePath))
	{
		return nullptr;
	}

	TSharedPtr<IImageWrapper> ImageWrapper = GetImageWrapperByExtention(ImagePath);

	if (ImageWrapper.IsValid() && ImageWrapper->SetCompressed(CompressedData.GetData(), CompressedData.Num()))
	{
		const TArray<uint8>* UncompressedRGBA = nullptr;

		if (ImageWrapper->GetRaw(ERGBFormat::RGBA, 8, UncompressedRGBA))
		{
			Texture = UTexture2D::CreateTransient(ImageWrapper->GetWidth(), ImageWrapper->GetHeight(), PF_R8G8B8A8);

			if (Texture != nullptr)
			{
				IsValid = true;

				OutWidth = ImageWrapper->GetWidth();
				OutHeight = ImageWrapper->GetHeight();

				void* TextureData = Texture->PlatformData->Mips[0].BulkData.Lock(LOCK_READ_WRITE);
				FMemory::Memcpy(TextureData, UncompressedRGBA->GetData(), UncompressedRGBA->Num());
				Texture->PlatformData->Mips[0].BulkData.Unlock();
				Texture->UpdateResource();
			}
		}
	}

	return Texture;
}

bool UReaperFunctionsLib::GenericArray_SortCompare(const UProperty* LeftProperty, void* LeftValuePtr, const UProperty* RightProperty, void* RightValuePtr)
{
	bool bResult = false;

	if (const UNumericProperty* LeftNumericProperty = Cast<const UNumericProperty>(LeftProperty))
	{
		if (LeftNumericProperty->IsFloatingPoint())
		{
			bResult = (LeftNumericProperty->GetFloatingPointPropertyValue(LeftValuePtr) < Cast<const UNumericProperty>(RightProperty)->GetFloatingPointPropertyValue(RightValuePtr));
		}
		else if (LeftNumericProperty->IsInteger())
		{
			bResult = (LeftNumericProperty->GetSignedIntPropertyValue(LeftValuePtr) < Cast<const UNumericProperty>(RightProperty)->GetSignedIntPropertyValue(RightValuePtr));
		}
	}
	else if (const UBoolProperty* LeftBoolProperty = Cast<const UBoolProperty>(LeftProperty))
	{
		bResult = (!LeftBoolProperty->GetPropertyValue(LeftValuePtr) && Cast<const UBoolProperty>(RightProperty)->GetPropertyValue(RightValuePtr));
	}
	else if (const UNameProperty* LeftNameProperty = Cast<const UNameProperty>(LeftProperty))
	{
		bResult = (LeftNameProperty->GetPropertyValue(LeftValuePtr).ToString() < Cast<const UNameProperty>(RightProperty)->GetPropertyValue(RightValuePtr).ToString());
	}
	else if (const UStrProperty* LeftStringProperty = Cast<const UStrProperty>(LeftProperty))
	{
		bResult = (LeftStringProperty->GetPropertyValue(LeftValuePtr) < Cast<const UStrProperty>(RightProperty)->GetPropertyValue(RightValuePtr));
	}
	else if (const UTextProperty* LeftTextProperty = Cast<const UTextProperty>(LeftProperty))
	{
		bResult = (LeftTextProperty->GetPropertyValue(LeftValuePtr).ToString() < Cast<const UTextProperty>(RightProperty)->GetPropertyValue(RightValuePtr).ToString());
	}

	return bResult;
}

void UReaperFunctionsLib::GenericArray_Sort(void* TargetArray, const UArrayProperty* ArrayProp, bool bAscendingOrder /* = true */, FName VariableName /* = NAME_None */)
{
	if (TargetArray)
	{
		FScriptArrayHelper ArrayHelper(ArrayProp, TargetArray);
		const int32 LastIndex = ArrayHelper.Num();

		if (const UObjectProperty* ObjectProperty = Cast<const UObjectProperty>(ArrayProp->Inner))
		{
			for (int32 i = 0; i < LastIndex; ++i)
			{
				for (int32 j = 0; j < LastIndex - 1 - i; ++j)
				{
					UObject* LeftObject = ObjectProperty->GetObjectPropertyValue(ArrayHelper.GetRawPtr(j));
					UObject* RightObject = ObjectProperty->GetObjectPropertyValue(ArrayHelper.GetRawPtr(j + 1));

					UProperty* LeftProperty = FindField<UProperty>(LeftObject->GetClass(), VariableName);
					UProperty* RightProperty = FindField<UProperty>(RightObject->GetClass(), VariableName);

					if (LeftProperty && RightProperty)
					{
						void* LeftValuePtr = LeftProperty->ContainerPtrToValuePtr<void>(LeftObject);
						void* RightValuePtr = RightProperty->ContainerPtrToValuePtr<void>(RightObject);

						if (GenericArray_SortCompare(LeftProperty, LeftValuePtr, RightProperty, RightValuePtr) != bAscendingOrder)
						{
							ArrayHelper.SwapValues(j, j + 1);
						}
					}
				}
			}
		}
		else
		{
			UProperty* Property = nullptr;

			if (const UStructProperty* StructProperty = Cast<const UStructProperty>(ArrayProp->Inner))
			{
				Property = FindField<UProperty>(StructProperty->Struct, VariableName);
			}
			else
			{
				Property = ArrayProp->Inner;
			}

			if (Property)
			{
				for (int32 i = 0; i < LastIndex; ++i)
				{
					for (int32 j = 0; j < LastIndex - 1 - i; ++j)
					{
						void* LeftValuePtr = Property->ContainerPtrToValuePtr<void>(ArrayHelper.GetRawPtr(j));
						void* RightValuePtr = Property->ContainerPtrToValuePtr<void>(ArrayHelper.GetRawPtr(j + 1));

						if (GenericArray_SortCompare(Property, LeftValuePtr, Property, RightValuePtr) != bAscendingOrder)
						{
							ArrayHelper.SwapValues(j, j + 1);
						}
					}
				}
			}
		}
	}
}

void UReaperFunctionsLib::Array_Sort(const TArray<int32>& TargetArray, bool bAscendingOrder /* = true */, FName VariableName /* = NAME_None */)
{
	// We should never hit these!  They're stubs to avoid NoExport on the class.  Call the Generic* equivalent instead
	check(0);
}

bool UReaperFunctionsLib::StringIsEmpty(const FString& Target)
{
	return Target.IsEmpty();
}

//~~~~~~~~~ END OF CONTRIBUTED BY KRIS ~~~~~~~~~~~

//~~~ Key To Truth ~~~
//.cpp
//Append different text strings with optional pins.
FString UReaperFunctionsLib::AppendMultiple(FString A, FString B)
{
	FString Result = "";

	Result += A;
	Result += B;

	return Result;
}

//TESTING
static void TESTINGInternalDrawDebugCircle(const UWorld* InWorld, const FMatrix& TransformMatrix, float Radius, int32 Segments, const FColor& Color, bool bPersistentLines, float LifeTime, uint8 DepthPriority, float Thickness = 0)
{
	//this is how you can make cpp only internal functions!

}

#undef LOCTEXT_NAMESPACE

TArray<uint8> UReaperFunctionsLib::PixelstoImg(int32 Width, int32 Height, const TArray<FLinearColor>& ImagePixels, bool sRGB) {
	//Create FColor version
	TArray<FColor> ColorArray;
	for (const FLinearColor& Each : ImagePixels)
	{
		ColorArray.Add(Each.ToFColor(sRGB));
	}

	TArray<uint8> CompressedPNG;
	FImageUtils::CompressImageArray(
		Width,
		Height,
		ColorArray,
		CompressedPNG
	);
	
	return CompressedPNG;		 
}

bool UReaperFunctionsLib::TexturetoPixelsArray(UTexture2D* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray)
{
	if (!T2D)
	{
		return false;
	}

	//To prevent overflow in BP if used in a loop
	PixelArray.Empty();

	T2D->SRGB = false;
	T2D->CompressionSettings = TC_VectorDisplacementmap;

	//Update settings
	T2D->UpdateResource();

	FTexture2DMipMap& MyMipMap = T2D->PlatformData->Mips[0];
	TextureWidth = MyMipMap.SizeX;
	TextureHeight = MyMipMap.SizeY;

	FByteBulkData* RawImageData = &MyMipMap.BulkData;

	if (!RawImageData)
	{
		return false;
	}

	FColor* RawColorArray = static_cast<FColor*>(RawImageData->Lock(LOCK_READ_ONLY));

	for (int32 x = 0; x < TextureWidth; x++)
	{
		for (int32 y = 0; y < TextureHeight; y++)
		{
			PixelArray.Add(RawColorArray[x * TextureWidth + y]);
		}
	}

	RawImageData->Unlock();
	return true;
}