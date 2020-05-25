// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "CoreMinimal.h"
#include "Kismet/BlueprintFunctionLibrary.h"
#include "ReaperFunctionsLib.generated.h"

/**
 * 
 */
UCLASS()
class VRARCHI_API UReaperFunctionsLib : public UBlueprintFunctionLibrary
{
	GENERATED_BODY()
	
		UFUNCTION(Category = "Reaper Funtions", BlueprintPure, Meta = (DisplayName = "IsEmpty"))
			static bool StringIsEmpty(const FString& Target);

		UFUNCTION(BlueprintPure, meta = (DisplayName = "Append Multiple", Keywords = "concatenate combine append strings", CommutativeAssociativeBinaryOperator = "true"), Category = "Reaper Funtions|String")
			static FString AppendMultiple(FString A, FString B);

		UFUNCTION(BlueprintPure, Category = "eaper Funtions|Random")
			static int32 RandInt_MINMAX_uniDis(int32 iMin, int32 iMax);

		UFUNCTION(BlueprintCallable, Category = "Reaper Funtions", meta = (Keywords = "create image png jpg jpeg bmp bitmap ico icon exr icns"))
			static TArray<uint8> PixelstoImg(int32 Width, int32 Height, const TArray<FLinearColor>& ImagePixels, bool sRGB);

		UFUNCTION(BlueprintCallable, Category = "Reaper Funtions", meta = (Keywords = "create image png jpg jpeg bmp bitmap ico icon exr icns"))
			static bool TexturetoPixelsArray(UTexture2D* T2D, int32& TextureWidth, int32& TextureHeight, TArray<FLinearColor>& PixelArray);

	public:
};
