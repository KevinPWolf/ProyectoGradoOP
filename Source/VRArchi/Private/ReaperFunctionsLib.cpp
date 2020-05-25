// Fill out your copyright notice in the Description page of Project Settings.

#include "ReaperFunctionsLib.h"
#include <Runtime\Engine\Public\ImageUtils.h>
#include "ImageUtils.h"
#include "IImageWrapper.h"
#include "IImageWrapperModule.h"

#include <chrono>
#include <random>

#include <string>

bool UReaperFunctionsLib::StringIsEmpty(const FString& Target)
{
	return Target.IsEmpty();
}

FString UReaperFunctionsLib::AppendMultiple(FString A, FString B)
{
	FString Result = "";

	Result += A;
	Result += B;

	return Result;
}

std::random_device rd;
unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();

std::mt19937 rand_MT;
std::default_random_engine rand_DRE;

int32 UReaperFunctionsLib::RandInt_MINMAX_uniDis(int32 iMin, int32 iMax)
{
	std::uniform_int_distribution<int32> dis(iMin, iMax);
	return dis(rand_DRE);
}

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