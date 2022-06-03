package com.uramnoil.serverist.auth.domain.repositories

import com.uramnoil.serverist.auth.domain.models.user.User
import com.uramnoil.serverist.common.domain.models.kernel.UserId

/**
 * Authenticated User用のリポジトリ
 * findById以外のクエリ系メソッドは、アプリケーション層に直接記述する。
 */
interface UserRepository {
    /**
     * 挿入
     * @param authenticatedUser  新規作成したいユーザ
     * @return データベース・通信系のエラー
     */
    suspend fun insert(authenticatedUser: User): Result<Unit>

    /**
     * 更新
     * @param authenticatedUser  更新したいユーザ
     * @return データベース・通信系のエラー
     */
    suspend fun update(authenticatedUser: User): Result<Unit>

    /**
     * 削除
     * @param authenticatedUser  削除したいユーザ
     * @return データベース・通信系のエラー
     */
    suspend fun delete(authenticatedUser: User): Result<Unit>

    /**
     * Idで検索
     * @param userId    目的のユーザのID
     * @return データベース・通信系のエラー
     */
    suspend fun findById(userId: UserId): Result<User?>
}
