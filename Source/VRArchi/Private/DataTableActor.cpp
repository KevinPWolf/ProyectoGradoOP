// Fill out your copyright notice in the Description page of Project Settings.



#include "DataTableActor.h"
#include <Runtime/Core/Public/Misc/Paths.h>
#include <Runtime/Core/Public/Misc/FileHelper.h>
#include "MyDataTable.h"
#include <Runtime/CoreUObject/Public/UObject/ConstructorHelpers.h>
#include <Runtime/Engine/Classes/Engine/Engine.h>


// Sets default values
ADataTableActor::ADataTableActor()
{
	// Set this actor to call Tick() every frame.  You can turn this off to improve performance if you don't need it.
	PrimaryActorTick.bCanEverTick = true;

	ConstructorHelpers::FObjectFinder<UDataTable> floordatatable_BP(TEXT("DataTable '/Game/JSon/FloorCSV.FloorCSV'"));

	if (floordatatable_BP.Object)
	{
		UE_LOG(LogTemp, Warning, TEXT("get datatable"));
		//GEngine->AddOnScreenDebugMessage(-1, 5.0f, FColor::Yellow, FString::Printf(TEXT("get datatable")));
		mydatatable = floordatatable_BP.Object;
	}

}

// Called when the game starts or when spawned
void ADataTableActor::BeginPlay()
{
	Super::BeginPlay();

}

// Called every frame
void ADataTableActor::Tick(float DeltaTime)
{
	Super::Tick(DeltaTime);

}

UDataTable* ADataTableActor::ReadCsvFile(FString csvPath)
{
	FString csvFile = FPaths::ProjectContentDir() + "/JSon/" + csvPath;
	if (FPaths::FileExists(csvFile))
	{
		FString FileContent;
		//Read the csv file  
		FFileHelper::LoadFileToString(FileContent, *csvFile);
		TArray<FString> problems = mydatatable->CreateTableFromJSONString(FileContent);

		if (problems.Num() > 0)
		{
			for (int32 ProbIdx = 0; ProbIdx < problems.Num(); ProbIdx++)
			{
				//Log the errors  
				UE_LOG(LogTemp, Warning, TEXT("Problem with reimport!"));
			}
		}
		else
		{
			//Updated Successfully  
			UE_LOG(LogTemp, Warning, TEXT("Successful reimport!"));
		}
	}
	return mydatatable;
}