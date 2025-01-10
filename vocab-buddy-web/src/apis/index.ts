import fetch, { pureFetch } from "@/requests"

import { generateAliyunRequestUrl } from "@/utils/AliyunUtil"

import Paginated from "@/models/basic/Paginated"
import Response from "@/models/basic/Response"
import Client from "@/models/biz/Client"
import Admin from "@/models/biz/Admin";
import UserLogin from "@/models/biz/UserLogin"
import SupportedRegions from "@/models/biz/SupportedRegions";
import Register from "@/models/biz/Register";
import RegisterParams from "@/models/request/RegisterParams";
import RegisterParamsV3 from "@/models/request/RegisterParamsV3"
import Dictation from "@/models/biz/Dictation"
import AliyunToken from "@/models/biz/AliyunToken"
import Tag from "@/models/biz/Tag"
import SaveDictationParams from "@/models/request/SaveDictationParams"

/**
 * @description 用户登录接口_v3
 * @param {string} username 
 * @param {string} password 
 * @returns {Promise<Response<Client>>} 
 */
function userLoginV3(username: string, password: string): Promise<Response<Client>> {
  return fetch.post("/auth/user/login", {
    username,
    password,
  })
}

/**
 * @description 管理员登陆接口_v3
 * @param username 
 * @param password 
 * @returns {Promise<Response<Admin>>}
 */
function adminLoginV3(username: string, password: string): Promise<Response<Admin>> {
  return fetch.post("/auth/admin/login", {
    username,
    password,
  })
}

/**
 * @description 注册账户接口_v3
 * @param {RegisterParamsV3} params 
 * @returns {Promise<Response<Register>>}
 */
function userRegisterV3(params: RegisterParamsV3): Promise<Response<Register>> {
  return fetch.post('/auth/user/register', params)
}

/**
 * @description 用户登录_v2,admin和client二合一
 * @param {string} username 用户名
 * @param {string} password 密码
 * @returns {Promise<Response<UserLogin>>} 响应
 */
function userLogin(username: string, password: string): Promise<Response<UserLogin>> {
  return fetch.post("/auth/login", {
    username,
    password,
  })
}

/**
 * @description 注册用户账号_v2
 * @param {RegisterParams} params 请求参数
 * @returns {Promise<Response<Register>>} 响应
 */
function userRegister(params: RegisterParams): Promise<Response<Register>> {
  return fetch.post("/auth/register", params)
}

/**
 * 用户登录
 * @param {string} username 用户名
 * @param {string} password 密码
 * @returns {Promise<Response<Client>>} 响应
 */
function clientLogin(username: string, password: string): Promise<Response<Client>> {
  return fetch.post("/auth/client/login", {
    username,
    password,
  })
}

/**
 * 管理员登录
 * @param {string} username 用户名
 * @param {string} password 密码
 * @returns {Promise<Response<Admin>>} 响应
 */
function adminLogin(username: string, password: string): Promise<Response<Admin>> {
  return fetch.post("/auth/admin/login", {
    username,
    password,
  })
}

/**
 * 获取支持的国家或地区代码
 * @returns {Promise<Response<SupportedRegions>>} 响应
 */
function getSupportedRegions(): Promise<Response<SupportedRegions[]>> {
  return fetch.get("/public/supported-regions")
}

/**
 * 注册用户账号
 * @param {RegisterParams} params 请求参数
 * @returns {Promise<Response<Register>>} 响应
 */
function register(params: RegisterParams): Promise<Response<Register>> {
  return fetch.post("/auth/client/register", params)
}

/**
 * 获取听写历史记录
 * @param {number} current 当前页码
 * @param {number} size 页面大小
 * @returns {Promise<Response>} 带有听写历史记录的响应
 */
function getDictationHistories(current: number, size: number): Promise<Response<Paginated<Dictation>>> {
  const queryParams: URLSearchParams = new URLSearchParams()
  queryParams.append("current", `${current}`)
  queryParams.append("size", `${size}`)
  return fetch.get(`/dictation/historical-dictations?${queryParams.toString()}`)
}

/**
 * 获取阿里云 NLS Token
 */
function getAliyunToken(): Promise<AliyunToken> {
  return pureFetch.get(generateAliyunRequestUrl())
}

/**
 * 获取听写详细信息
 * @param {string} dictationId
 * @returns {Promise<Response<Dictation>>}
 */
function getDictationInformation(dictationId: string): Promise<Response<Dictation>> {
  return fetch.get(`/dictation/${dictationId}`)
}

/**
 * 创建听写任务
 * @param {string} tagId 默写单词的标签ID
 * @param {number} wordsCount 需要的单词数量
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function getDictationExercise(answeringMode: number, generationMode: number, tagId?: string, wordsCount?: number): Promise<Response<any>> {
  return fetch.post("/dictation/generate", { answeringMode, generationMode, tagId, wordsCount })
}

/**
 * 获取所有的单词标签
 * @returns {Promise<Response<Tag[]>>}
 */
function getAllTags(): Promise<Response<Tag[]>> {
  // return fetch.get("/client/tags")
  return fetch.get("/tag/list")
}

/**
 * @description 听写训练的单次提交
 * @param dictationId 当前训练的id
 * @param data 当前提交的数据
 */
function saveDictationDetail(dictationId: string, data: SaveDictationParams): Promise<Response<null>> {
  return fetch.post(`/dictation-detail/${dictationId}`, data)
}

/**
 * 停止训练任务
 * @param dictationId 训练任务id
 */
function stopDictation(dictationId: string): Promise<Response<null>> {
  return fetch.get(`/dictation/${dictationId}/stop`)
}

export {
  userLoginV3,
  adminLoginV3,
  userRegisterV3,
  userLogin,
  userRegister,
  clientLogin,
  adminLogin,
  getSupportedRegions,
  register,
  getDictationHistories,
  getAliyunToken,
  getDictationInformation,
  getDictationExercise,
  getAllTags,
  saveDictationDetail,
  stopDictation,
}
