// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "Engine.h" 
#include "Kismet/BlueprintFunctionLibrary.h"
#include "ScaleImage.generated.h"


UCLASS()
class VRARCHI_API UScaleImage : public UBlueprintFunctionLibrary
{
	GENERATED_BODY()

		UFUNCTION(BlueprintCallable, Category = "Character|TakeScreenshot")
			static void TakeScreenshot(int X, int Y);

	public:
};
