// Fill out your copyright notice in the Description page of Project Settings.


#include "ScaleImage.h"

void UScaleImage::TakeScreenshot(int X, int Y)
{
    if (GEngine)
    {
        UGameViewportClient* GameViewport = GEngine->GameViewport;
        if (GameViewport)
        {
            FViewport* Viewport = GameViewport->Viewport;
            if (Viewport)
            {
                GScreenshotResolutionX = X;
                GScreenshotResolutionY = Y;
                Viewport->TakeHighResScreenShot();
                
            }
        }
    }
}