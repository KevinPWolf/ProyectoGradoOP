// Fill out your copyright notice in the Description page of Project Settings.

//to prevent nodes from getting called in constructors:
//meta=(UnsafeDuringActorConstruction = "true")

#include "CoreMinimal.h"
#include "Kismet/BlueprintFunctionLibrary.h"

//~~~~~~~~~~~~ UMG ~~~~~~~~~~~~~~~
//#include "Runtime/UMG/Public/UMG.h"
//#include "Runtime/UMG/Public/UMGStyle.h"
//#include "Runtime/UMG/Public/Slate/SObjectWidget.h"
//#include "Runtime/UMG/Public/IUMGModule.h"
//#include "Runtime/UMG/Public/Blueprint/UserWidget.h"
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#include "Runtime/Engine/Classes/Engine/LevelStreamingDynamic.h"

//Texture2D
#include "Engine/TextureRenderTarget2D.h"
#include "Engine/Texture2DDynamic.h"
#include "Engine/SceneCapture2D.h"
#include "Components/SceneCaptureComponent2D.h"
//#include "Engine/Texture2D.h"
//#include "DDSLoader.h"


#include "ReaperFunctionsLib.generated.h"

//~~~~~~~~~~~~~~~~~~~~~~
//			Key Modifiers
//~~~~~~~~~~~~~~~~~~~~~~
UENUM(BlueprintType)
enum class EJoyImgFormats : uint8
{
	JPG		UMETA(DisplayName = "JPG        "),
	PNG		UMETA(DisplayName = "PNG        "),
	BMP		UMETA(DisplayName = "BMP        "),
	ICO		UMETA(DisplayName = "ICO        "),
	EXR		UMETA(DisplayName = "EXR        "),
	ICNS		UMETA(DisplayName = "ICNS        ")
};

USTRUCT(BlueprintType)
struct FLevelStrmInstanceInfo
{
	GENERATED_USTRUCT_BODY()

		UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		FName PackageName;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		FName PackageNameToLoad;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		FVector Location;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		FRotator Rotation;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		uint8 bShouldBeLoaded : 1;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		uint8 bShouldBeVisible : 1;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		uint8 bShouldBlockOnLoad : 1;

	UPROPERTY(Category = "LevelStreaming", BlueprintReadWrite)
		int32 LODIndex;

	FLevelStrmInstanceInfo() {}

	FLevelStrmInstanceInfo(ULevelStreamingDynamic* LevelInstance);

	FString ToString() const
	{
		return FString::Printf(TEXT("PackageName: %s\nPackageNameToLoad:%s\nLocation:%s\nRotation:%s\nbShouldBeLoaded:%s\nbShouldBeVisible:%s\nbShouldBlockOnLoad:%s\nLODIndex:%i")
			, *PackageName.ToString()
			, *PackageNameToLoad.ToString()
			, *Location.ToString()
			, *Rotation.ToString()
			, (bShouldBeLoaded) ? TEXT("True") : TEXT("False")
			, (bShouldBeVisible) ? TEXT("True") : TEXT("False")
			, (bShouldBlockOnLoad) ? TEXT("True") : TEXT("False")
			, LODIndex);
	}
};

UCLASS()
class VRARCHI_API UReaperFunctionsLib : public UBlueprintFunctionLibrary
{
	GENERATED_BODY()

		UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Misc")
		static UTextureRenderTarget2D* CreateTextureRenderTarget2D(int32 Width = 256, int32 Height = 256, FLinearColor ClearColor = FLinearColor::White, float Gamma = 1)
	{
		UTextureRenderTarget2D* NewRenderTarget2D = NewObject<UTextureRenderTarget2D>();
		if (!NewRenderTarget2D)
		{
			return nullptr;
		}
		NewRenderTarget2D->ClearColor = FLinearColor::White;
		NewRenderTarget2D->TargetGamma = Gamma;
		NewRenderTarget2D->InitAutoFormat(Width, Height);
		return NewRenderTarget2D;
	}

	//~~~~~~~~~~
// 	File I/O
//~~~~~~~~~~

/** Obtain all files in a provided directory, with optional extension filter. All files are returned if Ext is left blank. Returns false if operation could not occur. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|File IO")
		static bool JoyFileIO_GetFiles(TArray<FString>& Files, FString RootFolderFullPath, FString Ext);

	/** Obtain all files in a provided root directory, including all subdirectories, with optional extension filter. All files are returned if Ext is left blank. The full file path is returned because the file could be in any subdirectory. Returns false if operation could not occur. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|File IO")
		static bool JoyFileIO_GetFilesInRootAndAllSubFolders(TArray<FString>& Files, FString RootFolderFullPath, FString Ext);

	/** Obtain a listing of all SaveGame file names that were saved using the Blueprint Save Game system. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|File IO")
		static void SaveGameObject_GetAllSaveSlotFileNames(TArray<FString>& FileNames);

	/** Returns false if the new file could not be created. The folder path must be absolute, such as C:\Users\Self\Documents\YourProject\MyPics. You can use my other Paths nodes to easily get absolute paths related to your project! <3 Rama */
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Screenshots", meta = (Keywords = "High resolution"))
		static bool ScreenShots_Rename_Move_Most_Recent(FString& OriginalFileName, FString NewName, FString NewAbsoluteFolderPath, bool HighResolution = true);

	/** Easily add to an integer! <3 Rama*/
	UFUNCTION(BlueprintCallable, meta = (CompactNodeTitle = "+=", Keywords = "increment integer"), Category = "Reaper Funtions|Math|Integer")
		static void VictoryIntPlusEquals(UPARAM(ref) int32& Int, int32 Add, int32& IntOut);

	/** Easily subtract from an integer! <3 Rama*/
	UFUNCTION(BlueprintCallable, meta = (CompactNodeTitle = "-=", Keywords = "decrement integer"), Category = "Reaper Funtions|Math|Integer")
		static void VictoryIntMinusEquals(UPARAM(ref) int32& Int, int32 Sub, int32& IntOut);

	/** Easily add to a float! <3 Rama*/
	UFUNCTION(BlueprintCallable, meta = (CompactNodeTitle = "+=", Keywords = "increment float"), Category = "Reaper Funtions|Math|Float")
		static void VictoryFloatPlusEquals(UPARAM(ref) float& Float, float Add, float& FloatOut);

	/** Easily subtract from a float! <3 Rama*/
	UFUNCTION(BlueprintCallable, meta = (CompactNodeTitle = "-=", Keywords = "decrement float"), Category = "Reaper Funtions|Math|Float")
		static void VictoryFloatMinusEquals(UPARAM(ref) float& Float, float Sub, float& FloatOut);

	/** Sort an integer array, smallest value will be at index 0 after sorting. Modifies the input array, no new data created. <3 Rama */
	UFUNCTION(BlueprintCallable, meta = (Keywords = "sort integer array"), Category = "Reaper Funtions|Array")
		static void VictorySortIntArray(UPARAM(ref) TArray<int32>& IntArray, TArray<int32>& IntArrayRef);

	/** Sort a float array, smallest value will be at index 0 after sorting. Modifies the input array, no new data created. */
	UFUNCTION(BlueprintCallable, meta = (Keywords = "sort float array"), Category = "Reaper Funtions|Array")
		static void VictorySortFloatArray(UPARAM(ref) TArray<float>& FloatArray, TArray<float>& FloatArrayRef);


	/* Returns true if vector2D A is equal to vector2D B (A == B) within a specified error tolerance */
	UFUNCTION(BlueprintPure, meta = (DisplayName = "Equal (vector2D)", CompactNodeTitle = "==", Keywords = "== equal"), Category = "Reaper Funtions|Math|Vector2D")
		static bool EqualEqual_Vector2DVector2D(FVector2D A, FVector2D B, float ErrorTolerance = 1.e-4f)
	{
		return A.Equals(B, ErrorTolerance);
	}

	/* Returns true if vector2D A is not equal to vector2D B (A != B) within a specified error tolerance */
	UFUNCTION(BlueprintPure, meta = (DisplayName = "Not Equal (vector2D)", CompactNodeTitle = "!=", Keywords = "!= not equal"), Category = "Victory BP Library|Math|Vector2D")
		static bool NotEqual_Vector2DVector2D(FVector2D A, FVector2D B, float ErrorTolerance = 1.e-4f)
	{
		return !A.Equals(B, ErrorTolerance);
	}

	//~~~

	//~~~ Text To Number ~~~

	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Conversion")
		static bool Text_IsNumeric(const FText& Text)
	{
		return Text.IsNumeric();
	}

	static void TextNumFormat(FString& StrNum, bool UseDotForThousands)
	{
		//10.000.000,997
		if (UseDotForThousands)
		{
			StrNum.ReplaceInline(TEXT("."), TEXT(""));	//no dots as they truncate
			StrNum.ReplaceInline(TEXT(","), TEXT("."));	//commas become decimal
		}

		//10,000,000.997
		else
		{
			StrNum.ReplaceInline(TEXT(","), TEXT(""));  //decimal can stay, commas would truncate so remove
		}
	}

	//~~~ End of Text To Number ~~~

	/** Convert String Back To Color. IsValid indicates whether or not the string could be successfully converted. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Conversion", meta = (DisplayName = "String to Color", CompactNodeTitle = "->"))
		static void Conversions__StringToColor(const FString& String, FLinearColor& ConvertedColor, bool& IsValid);

	/** Convert Color to String! */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Conversion", meta = (DisplayName = "Color to String ", CompactNodeTitle = "~>"))
		static void Conversions__ColorToString(const FLinearColor& Color, FString& ColorAsString);

	/** Make sure to save off the return value as a global variable in one of your BPs or else it will get garbage collected! */
	UFUNCTION(BlueprintCallable, Category = "Reaper FuntionsMisc", meta = (DeprecatedFunction, DeprecationMessage = "Epic has introduced Construct Object as of 4.9.0, I recommend you use that instead! -Rama", HidePin = "WorldContextObject", DefaultToSelf = "WorldContextObject"))
		static UObject* CreateObject(UObject* WorldContextObject, UClass* TheObjectClass);

	/** Make sure to save off the return value as a global variable in one of your BPs or else it will get garbage collected! */
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Misc", meta = (HidePin = "WorldContextObject", DefaultToSelf = "WorldContextObject"))
		static UPrimitiveComponent* CreatePrimitiveComponent(UObject* WorldContextObject, TSubclassOf<UPrimitiveComponent> CompClass, FName Name, FVector Location, FRotator Rotation);

	/** Returns which platform the game code is running in.*/
	UFUNCTION(BlueprintCallable, BlueprintPure, Category = "Reaper Funtions|System")
		static void OperatingSystem__GetCurrentPlatform(
			bool& Windows_,
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
		);

	//~~~

	//~~~

	/** Loads a text file from hard disk and parses it into a String array, where each entry in the string array is 1 line from the text file. Option to exclude lines that are only whitespace characters or '\n'. Returns the size of the final String Array that was created. Returns false if the file could be loaded from hard disk. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|File IO")
		static bool LoadStringArrayFromFile(TArray<FString>& StringArray, int32& ArraySize, FString FullFilePath = "Enter Full File Path", bool ExcludeEmptyLines = false);

	/** Load a text file to a single string that you can use ParseIntoArray on newline characters if you want same format as LoadStringArrayFromFile. This version supports unicode characters! */
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|File IO")
		static bool LoadStringFromFile(FString& Result, FString FullFilePath = "Enter Full File Path")
	{
		return FFileHelper::LoadFileToString(Result, *FullFilePath);
	}

	//~~~

	/** Max of all array entries. Returns -1 if the supplied array is empty. Returns the index of the max value as well as the value itself. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Array")
		static void MaxOfFloatArray(const TArray<float>& FloatArray, int32& IndexOfMaxValue, float& MaxValue);

	/** Max of all array entries. Returns -1 if the supplied array is empty. Returns the index of the max value as well as the value itself. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Array")
		static void MaxOfIntArray(const TArray<int32>& IntArray, int32& IndexOfMaxValue, int32& MaxValue);

	/** Min of all array entries. Returns -1 if the supplied array is empty. Returns the index of the min value as well as the value itself. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Array")
		static void MinOfFloatArray(const TArray<float>& FloatArray, int32& IndexOfMinValue, float& MinValue);

	/** Min of all array entries. Returns -1 if the supplied array is empty. Returns the index of the min value as well as the value itself. */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Array")
		static void MinOfIntArray(const TArray<int32>& IntArray, int32& IndexOfMinValue, int32& MinValue);

	//~~~

	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|String")
		static bool HasSubstring(const FString& SearchIn, const FString& Substring, ESearchCase::Type SearchCase = ESearchCase::IgnoreCase, ESearchDir::Type SearchDir = ESearchDir::FromStart);

	/** Combines two strings together! The Separator and the Labels are optional*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|String")
		static FString String__CombineStrings(FString StringFirst, FString StringSecond, FString Separator = "", FString StringFirstLabel = "", FString StringSecondLabel = "");

	/** Separator is always a space */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|String", meta = (Keywords = "concatenate append", CommutativeAssociativeBinaryOperator = "true"))
		static FString String__CombineStrings_Multi(FString A, FString B);

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//			  			Paths

	/** InstallDir/GameName/Binaries/Win64 */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__Win64Dir_BinaryLocation();

	/** InstallDir/WindowsNoEditor/ */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__WindowsNoEditorDir();

	/** InstallDir/GameName */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__GameRootDirectory();

	/** InstallDir/GameName/Saved */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__SavedDir();

	/** InstallDir/GameName/Saved/Config/ */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__ConfigDir();

	/** InstallDir/GameName/Saved/Screenshots/Windows */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__ScreenShotsDir();

	/** InstallDir/GameName/Saved/Logs */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Paths")
		static FString VictoryPaths__LogsDir();

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~

	//Min and Max of Array
	static FORCEINLINE float Min(const TArray<float>& Values, int32* MinIndex = NULL)
	{
		if (MinIndex)
		{
			*MinIndex = 0;
		}

		if (Values.Num() <= 0)
		{
			return -1;
		}

		float CurMin = Values[0];
		for (const float EachValue : Values)
		{
			CurMin = FMath::Min(CurMin, EachValue);
		}

		if (MinIndex)
		{
			*MinIndex = Values.Find(CurMin);
		}
		return CurMin;
	}
	static FORCEINLINE float Max(const TArray<float>& Values, int32* MaxIndex = NULL)
	{
		if (MaxIndex)
		{
			*MaxIndex = 0;
		}

		if (Values.Num() <= 0)
		{
			return -1;
		}

		float CurMax = Values[0];
		for (const float EachValue : Values)
		{
			CurMax = FMath::Max(CurMax, EachValue);
		}

		if (MaxIndex)
		{
			*MaxIndex = Values.Find(CurMax);
		}
		return CurMax;
	}

	static FORCEINLINE int32 Min(const TArray<int32>& Values, int32* MinIndex = NULL)
	{
		if (MinIndex)
		{
			*MinIndex = 0;
		}

		if (Values.Num() <= 0)
		{
			return -1;
		}

		int32 CurMin = Values[0];
		for (const int32 EachValue : Values)
		{
			CurMin = FMath::Min(CurMin, EachValue);
		}

		if (MinIndex)
		{
			*MinIndex = Values.Find(CurMin);
		}
		return CurMin;
	}
	static FORCEINLINE int32 Max(const TArray<int32>& Values, int32* MaxIndex = NULL)
	{
		if (MaxIndex)
		{
			*MaxIndex = 0;
		}

		if (Values.Num() <= 0)
		{
			return -1;
		}

		int32 CurMax = Values[0];
		for (const int32 EachValue : Values)
		{
			CurMax = FMath::Max(CurMax, EachValue);
		}

		if (MaxIndex)
		{
			*MaxIndex = Values.Find(CurMax);
		}
		return CurMax;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/** Load a Texture2D from a JPG,PNG,BMP,ICO,EXR,ICNS file! IsValid tells you if file path was valid or not. Enjoy! -Rama */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Load Texture From File", meta = (Keywords = "image png jpg jpeg bmp bitmap ico icon exr icns"))
		static UTexture2D* Victory_LoadTexture2D_FromFile(const FString& FullFilePath, EJoyImgFormats ImageFormat, bool& IsValid, int32& Width, int32& Height);

	/** Load a Texture2D from a JPG,PNG,BMP,ICO,EXR,ICNS file! IsValid tells you if file path was valid or not. Enjoy! -Rama */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Load Texture From File", meta = (Keywords = "image png jpg jpeg bmp bitmap ico icon exr icns"))
		static UTexture2D* Victory_LoadTexture2D_FromFile_Pixels(const FString& FullFilePath, EJoyImgFormats ImageFormat, bool& IsValid, int32& Width, int32& Height, TArray<FLinearColor>& OutPixels);

	/** Retrieve a pixel color value given the pixel array, the image height, and the coordinates. Returns false if the coordinates were not valid. Pixel coordinates start from upper left corner as 0,0. X= horizontal, Y = vertical */
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Load Texture From File", meta = (Keywords = "image coordinate index map value"))
		static bool Victory_Get_Pixel(const TArray<FLinearColor>& Pixels, int32 ImageHeight, int32 x, int32 y, FLinearColor& FoundColor);

	/** Save an array of pixels to disk as a PNG! It is very important that you supply the curret width and height of the image! Returns false if Width * Height != Array length or file could not be saved to the location specified. I return an ErrorString to clarify what the exact issue was. -Rama */
	
	/** This will modify the original T2D to remove sRGB and change compression to VectorDisplacementMap to ensure accurate pixel reading. -Rama*/
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Load Texture From File", meta = (Keywords = "create image png jpg jpeg bmp bitmap ico icon exr icns"))
		static bool Victory_GetPixelFromT2D(UTexture2D* T2D, int32 X, int32 Y, FLinearColor& PixelColor);

	/** This will modify the original T2D to remove sRGB and change compression to VectorDisplacementMap to ensure accurate pixel reading. -Rama*/
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Load Texture From File", meta = (Keywords = "create image png jpg jpeg bmp bitmap ico icon exr icns"))
		static bool Victory_GetPixelsArrayFromT2D(UTexture2D* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray);

	/** This will modify the original T2D to remove sRGB and change compression to VectorDisplacementMap to ensure accurate pixel reading. -Rama*/
	//UFUNCTION(BlueprintCallable, Category = "Victory BP Library|Load Texture From File",meta=(Keywords="create image png jpg jpeg bmp bitmap ico icon exr icns"))
	static bool Victory_GetPixelsArrayFromT2DDynamic(UTexture2DDynamic* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray);

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~

		UFUNCTION(BlueprintCallable, Category = "Reaper Funtions", meta = (Keywords = "create image png jpg jpeg bmp bitmap ico icon exr icns"))
			static TArray<uint8> PixelstoImg(int32 Width, int32 Height, const TArray<FLinearColor>& ImagePixels, bool sRGB);

		UFUNCTION(BlueprintCallable, Category = "Reaper Funtions", meta = (Keywords = "create image png jpg jpeg bmp bitmap ico icon exr icns"))
			static bool TexturetoPixelsArray(UTexture2D* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray);

		//----------------------------------------------------------------------------------------------BeginRANDOM
public:
	/** Construct a random device, returns either a random device or the default random engine; system dependant;
	*/
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Random")
		static void constructRand();

	/** Seed Rand with value passed
	* @param seed - value to pass to the prng as the seed
	*/
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Random")
		static void seedRand(int32 seed);

	/** Seed Rand with current time
	*/
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Random")
		static void seedRandWithTime();

	/** Seed Rand with entropy
	* @param seed - value to pass to the prng as the seed
	*/
	UFUNCTION(BlueprintCallable, Category = "Reaper Funtions|Random")
		static void seedRandWithEntropy();

	/** Random Bool - Bernoulli distribution
	* @param fBias - Bias of Bernoulli distribution
	* @return uniformly distributed bool based on bias parameter
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static bool RandBool_Bernoulli(float fBias);

	/** Random Integer - Zero to One Uniform distribution
	* @return int32 - uniform distribution from 0 to 1
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static int32 RandInt_uniDis();

	/** Random Integer - MIN to MAX Uniform distribution
	* @param iMin - Minimum value of uniform distribution
	* @param iMax - Maximum value of uniform distribution
	* @return int32 - uniform distribution from iMin to iMax parameters
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static int32 RandInt_MINMAX_uniDis(int32 iMin, int32 iMax);

	/** Random Double - Zero to One Uniform distribution
	* @return double - uniform distribution from 0 to 1
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static float RandFloat_uniDis();

	/** Random Double - Uniform distribution based on MIN to MAX parameters
	* @param iMin - Minimum value of uniform distribution
	* @param iMax - Maximum value of uniform distribution
	* @return double - uniform distribution from iMin to iMax parameters
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static float RandFloat_MINMAX_uniDis(float iMin, float iMax);

	/** Random Bool - Bernoulli distribution - Mersenne Twister
	* @param fBias - Bias of Bernoulli distribution
	* @return uniformly distributed bool based on bias parameter
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static bool RandBool_Bernoulli_MT(float fBias);

	/** Random Integer - Zero to One Uniform distribution - Mersenne Twister
	* @return int32 - uniform distribution from 0 to 1
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static int32 RandInt_uniDis_MT();

	/** Random Integer - MIN to MAX Uniform distribution - Mersenne Twister
	* @param iMin - Minimum value of uniform distribution
	* @param iMax - Maximum value of uniform distribution
	* @return int32 - uniform distribution from iMin to iMax parameters
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static int32 RandInt_MINMAX_uniDis_MT(int32 iMin, int32 iMax);

	/** Random Float - Zero to One Uniform distribution -  Mersenne Twister
	* @return float - uniform distribution from 0 to 1
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static float RandFloat_uniDis_MT();

	/** Random Float - Uniform distribution based on MIN to MAX parameters - Mersenne Twister
	* @param iMin - Minimum value of uniform distribution
	* @param iMax - Maximum value of uniform distribution
	* @return float - uniform distribution from iMin to iMax parameters
	*/
	UFUNCTION(BlueprintPure, Category = "Reaper Funtions|Random")
		static float RandFloat_MINMAX_uniDis_MT(float iMin, float iMax);
	//----------------------------------------------------------------------------------------------ENDRANDOM

	/** Currently the only supported format for this function is B8G8R8A8. Make sure to include the appropriate image extension in your file path! Recommended: .bmp, .jpg, .png. Contributed by Community Member Kris! */
	UFUNCTION(Category = "Victory BP Library|SceneCapture", BlueprintCallable)
		static bool CaptureComponent2D_SaveImage(class USceneCaptureComponent2D* Target, const FString ImagePath, const FLinearColor ClearColour);

	UFUNCTION(Category = "Reaper Funtions|SceneCapture", BlueprintCallable, Meta = (DefaultToSelf = "Target"))
		static bool Capture2D_SaveImage(class ASceneCapture2D* Target, const FString ImagePath, const FLinearColor ClearColour);

	/** Make sure your image path has a valid extension! Supported types can be seen in the BP node Victory_LoadTexture2D_FromFile. Contributed by Community Member Kris! */
	UFUNCTION(Category = "Reaper Funtions|Load Texture From File", BlueprintCallable)
		static UTexture2D* LoadTexture2D_FromFileByExtension(const FString& ImagePath, bool& IsValid, int32& OutWidth, int32& OutHeight);

	/**
	 * Recurses up the list of parents until it finds a widget of WidgetClass.
	 * @return widget that is Parent of ChildWidget that matches WidgetClass.
	 */

	static bool GenericArray_SortCompare(const UProperty* LeftProperty, void* LeftValuePtr, const UProperty* RightProperty, void* RightValuePtr);

	UFUNCTION(Category = "Reaper Funtions|Utilities|Array", BlueprintCallable, CustomThunk, Meta = (DisplayName = "Sort", ArrayParm = "TargetArray", AdvancedDisplay = "bAscendingOrder,VariableName"))
		static void Array_Sort(const TArray<int32>& TargetArray, bool bAscendingOrder = true, FName VariableName = NAME_None);

	static void GenericArray_Sort(void* TargetArray, const UArrayProperty* ArrayProp, bool bAscendingOrder = true, FName VariableName = NAME_None);

	DECLARE_FUNCTION(execArray_Sort)
	{
		Stack.MostRecentProperty = nullptr;
		Stack.StepCompiledIn<UArrayProperty>(NULL);
		void* ArrayAddr = Stack.MostRecentPropertyAddress;
		UArrayProperty* ArrayProperty = Cast<UArrayProperty>(Stack.MostRecentProperty);
		if (!ArrayProperty)
		{
			Stack.bArrayContextFailed = true;
			return;
		}

		P_GET_UBOOL(bAscendingOrder);

		P_GET_PROPERTY(UNameProperty, VariableName);

		P_FINISH;
		P_NATIVE_BEGIN;
		GenericArray_Sort(ArrayAddr, ArrayProperty, bAscendingOrder, VariableName);
		P_NATIVE_END;
	}

	UFUNCTION(Category = "Reaper Funtions|Utilities|String", BlueprintPure, Meta = (DisplayName = "IsEmpty"))
		static bool StringIsEmpty(const FString& Target);

	/* Addition of strings (A + B) with pins. Contributed by KeyToTruth */
	UFUNCTION(BlueprintPure, meta = (DisplayName = "Append Multiple", Keywords = "concatenate combine append strings", CommutativeAssociativeBinaryOperator = "true"), Category = "Reaper Funtions|String")
		static FString AppendMultiple(FString A, FString B);

};
