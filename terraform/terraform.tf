terraform {
    backend "s3" {
        bucket = "dbmatos-beerstore-terraform-state"
        key = "beerstore"
        region = "us-east-1"
        profile = "terraform"
    }
}