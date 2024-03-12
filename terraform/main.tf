terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.40.0"
    }
  }
}

provider "aws" {
  region = "us-east-2"
}

#VPC Setting
module "main_vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.5.3"

  name            = "main_vpc"
  cidr            = "10.0.0.0/16"
  private_subnets = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  public_subnets  = ["10.0.4.0/24", "10.0.5.0/24", "10.0.6.0/24"]
  azs             = ["us-east-2a", "us-east-2b", "us-east-2c"]

  enable_nat_gateway   = true
  single_nat_gateway   = true
  enable_dns_hostnames = true

  tags = {
    "kubernets.io/cluster/main-vpc" = "shared"
  }

  public_subnet_tags = {
    "kubernets.io/cluster/main-vpc" = "shared"
    "kubernets.io/role/elb"         = 1
  }

  private_subnet_tags = {
    "kubernets.io/cluster/main-vpc"  = "shared"
    "kubernets.io/role/internal-elb" = 1
  }

}
#VPC Setting

#DB Setting
resource "aws_db_instance" "db" {
  identifier           = "tech-challenge-db"
  engine               = "mysql"
  engine_version       = "8.0"
  instance_class       = "db.t2.micro"
  allocated_storage    = 10
  db_name              = "tech_challenge_db"
  username             = "tech_challenge"
  password             = "tech_challenge-password"
  parameter_group_name = "default.mysql8.0"
  skip_final_snapshot  = true
  port                 = 3306
}
#DB Setting

#ECR Setting
resource "aws_ecr_repository" "tech-challenge" {
  name                 = "tech-challenge"
  image_tag_mutability = "MUTABLE"
  force_delete         = true


  image_scanning_configuration {
    scan_on_push = true
  }
}
#ECR Setting

#ECS Setting
locals {
  split_endpoint = element(split(":", aws_db_instance.db.endpoint), 0)
}

resource "aws_ecs_cluster" "tech_challenge_cluster" {
  name = "tech_challenge_cluster"
}

resource "aws_ecs_task_definition" "tech_challenge_task" {
  family                   = "tech_challenge_task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"

  container_definitions = jsonencode(
    [
      {
        name : "tech-challenge-container",
        image : "tech_challenge:latest",
        cpu : 256,
        memory : 512,
        essential : true,
        portMappings : [
          {
            containerPort : 8080,
            hostPort : 8080
          }
        ],
        environment = [
          { name = "PORT", value = "8080" },
          { name = "DB_NAME", value = aws_db_instance.db.db_name },
          { name = "DB_HOST", value = local.split_endpoint },
          { name = "DB_PORT", value = "${tostring(aws_db_instance.db.port)}" },
          { name = "DB_USERNAME", value = aws_db_instance.db.username },
          { name = "DB_PASSWORD", value = "${aws_db_instance.db.username}-password" },
          { name = "NODE_ENV", value = "dev" },

        ]
      }
    ]
  )
}

resource "aws_ecs_service" "tech_challenge_service" {
  name            = "tech-challenge-service"
  cluster         = aws_ecs_cluster.tech_challenge_cluster.id
  task_definition = aws_ecs_task_definition.tech_challenge_task.arn
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = module.main_vpc.private_subnets
    security_groups = [module.main_vpc.default_security_group_id]
  }
}
#ECS Setting