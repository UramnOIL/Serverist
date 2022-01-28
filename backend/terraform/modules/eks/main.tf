module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "18.2.3"

  cluster_name    = "serverist-cluster"
  cluster_version = "1.21"
  subnets         = var.private_subnets
  vpc_id          = var.vpc_id

  node_groups = {
    my-nodegroup = {
      desired_capacity = 2
      max_capacity     = 2
      min_capacity     = 2
      instance_types   = ["t3.small"]
    }
  }
}