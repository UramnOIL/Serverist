package com.uramnoil.serverist.common.domain.exception

open class NotFoundException(message: String) : Exception(message)

class UserNotFoundByIdException(id: String) : NotFoundException("id: ${id}に一致するユーザーが見つかりませんでした。")
class ServerNotFoundByIdException(id: String) : NotFoundException("id: ${id}に一致するサーバーが見つかりませんでした。")
