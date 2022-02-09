data aws_eks_cluster "default" {
  name = var.cluster_name
}

data aws_eks_cluster_auth "default" {
  name = var.cluster_name
}

provider kubernetes {
  host                   = data.aws_eks_cluster.default.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.default.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.default.token
  load_config_file       = false
}

resource kubernetes_secret "database" {
  metadata {
    name = "database"
  }

  data = {
    username = "serverist"
    password = var.database_password
  }
}