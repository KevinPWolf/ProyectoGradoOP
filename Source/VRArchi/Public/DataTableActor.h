// Fill out your copyright notice in the Description page of Project Settings. VRARCHI_API

#pragma once

#include "CoreMinimal.h"
#include "GameFramework/Actor.h"
#include "DataTableActor.generated.h"

UCLASS()
class VRARCHI_API ADataTableActor : public AActor
{
	GENERATED_BODY()

public:
	// Sets default values for this actor's properties
	ADataTableActor();

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