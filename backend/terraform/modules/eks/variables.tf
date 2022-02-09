variable "vpc_id" {
  description = "AWS VPCのID"
  type        = string
}
variable "subnets" {
  description = "所属するサブネットのリスト"
  type        = list(string)
}