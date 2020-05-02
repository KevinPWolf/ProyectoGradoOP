// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "Engine.h" 
#include "Kismet/BlueprintFunctionLibrary.h"
#include "FileBrowser.generated.h"

/**
 * 
 */
UCLASS()
class VRARCHI_API UFileBrowser : public UBlueprintFunctionLibrary
{
	GENERATED_BODY()
	

		UFUNCTION(BlueprintCallable, Category = "FileBrowser", meta = (Keywords = "Open"))
			static void OpenFileDialog(FString DialogTitle, FString DefaultPath, FString FileTypes, TArray<FString>& OutFileNames);

	public:
};
