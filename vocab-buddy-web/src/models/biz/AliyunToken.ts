export default interface AliyunToken {
  ErrMsg?: string,
  Token?: {
    UserId: string,
    Id: string,
    ExpireTime: bigint
  },
  RequestId?: string,
  Message?: string,
  Recommend?: string,
  HostId?: string,
  Code?: string
}