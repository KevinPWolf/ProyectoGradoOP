// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "CoreMinimal.h"
#include "Kismet/BlueprintFunctionLibrary.h"
#include "MyDataTable.h"
#include "TextFileManagement.generated.h"

/**
 * 
 */
UCLASS()
class VRARCHI_API UTextFileManagement : public UBlueprintFunctionLibrary
{
	GENERATED_BODY()


		UFUNCTION(BlueprintCallable, Category = "Custom", meta = (Keywords = "Save"))
			static bool SaveArrayText(FString FileName, TArray<FString> SaveText, bool AllowOverwriting);
	
	public:
};
