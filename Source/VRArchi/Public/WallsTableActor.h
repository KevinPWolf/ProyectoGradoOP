// Fill out your copyright notice in the Description page of Project Settings.

#pragma once

#include "CoreMinimal.h"
#include "GameFramework/Actor.h"
#include "WallsTableActor.generated.h"

/**
 * 
 */
UCLASS()
class VRARCHI_API AWallsTableActor : public AActor
{
	GENERATED_BODY()

public:
	// Sets default values for this actor's properties
	AWallsTableActor();

protected:
	// Called when the game starts or when spawned
	virtual void BeginPlay() override;

public:
	// Called every frame
	virtual void Tick(float DeltaTime) override;

	UFUNCTION(BlueprintCallable, Category = "MySocket")
		UDataTable* ReadCsvFile(FString csvPath);

	class UDataTable* mydatatable;
};
