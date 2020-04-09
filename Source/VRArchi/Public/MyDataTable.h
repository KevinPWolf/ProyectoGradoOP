// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "CoreMinimal.h"
#include "Engine/DataTable.h"
#include "MyDataTable.generated.h"


/** Example Data */
USTRUCT(BlueprintType)
struct FExampleData : public FTableRowBase
{
    GENERATED_USTRUCT_BODY()

public:

    /** Name */
    UPROPERTY(EditAnywhere, BlueprintReadWrite, Category = Building)
        TArray<int32> Posicion;

};


/**
 *
 */
UCLASS()
class VRARCHI_API UMyDataTable : public UDataTable
{
	GENERATED_BODY()


public:



};
