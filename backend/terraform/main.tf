module "vpc" {
  source = "./modules/vpc"
}

module "eks" {
  source = "./modules/eks"

  vpc_id          = module.vpc.vpc_id
  private_subnets = module.vpc.serverist_subnets
}

module "kubernetes" {
  source = "./modules/kubernetes"
}