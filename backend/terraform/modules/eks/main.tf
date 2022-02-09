provider "aws" {
  region = local.region
}

locals {
  region = "ap-northeast-1"
}

module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "18.2.3"

  cluster_name    = "serverist-api-cluster"
  cluster_version = "1.21"
  vpc_id          = var.vpc_id
  subnets         = var.subnets

  # IPV6
  cluster_ip_family          = "ipv6"
  create_cni_ipv6_iam_policy = true

  eks_managed_node_groups = {
    serverist_api = {
      vpc_id     = var.vpc_id
      subnet_ids = var.subnets

      min_size     = 2
      max_size     = 2
      desired_size = 2

      instance_types = ["t3.small"]
      capacity_type  = "SPOT"

      labels = {
        App = "serverist_api"
      }
    }
  }
}