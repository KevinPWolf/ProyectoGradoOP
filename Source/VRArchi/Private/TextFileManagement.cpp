// Fill out your copyright notice in the Description page of Project Settings.


#include "TextFileManagement.h"
#include "Misc/FileHelper.h"
#include <Runtime/Core/Public/Misc/Paths.h>
#include "HAL/PlatformFilemanager.h"
#include <string>
//#include <Runtime/Json/Public/Json.h>
//#include <Runtime/JsonUtilities/Public/JsonUtilities.h>


bool UTextFileManagement::SaveArrayText(FString FileName, TArray<FString> SaveText, bool AllowOverwriting = false)
{
	//set complete file directory
	FString SaveDirectory = FPaths::ProjectContentDir();
	SaveDirectory += FileName;

	if (!AllowOverwriting)
	{
		if (FPlatformFileManager::Get().GetPlatformFile().FileExists(*SaveDirectory))
		{
			return false;
		}
	}

	FString OutputString="";
	for (FString& Each : SaveText)
	{
		OutputString += Each;
		OutputString += LINE_TERMINATOR;
	}
	//ContainerObject->SetArrayField("", values);
	//Serializar Json
	

	return FFileHelper::SaveStringToFile(OutputString, *SaveDirectory);
}
