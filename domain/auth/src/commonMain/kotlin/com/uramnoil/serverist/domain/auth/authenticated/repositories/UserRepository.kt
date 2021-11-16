package com.uramnoil.serverist.domain.auth.authenticated.repositories

import com.uramnoil.serverist.domain.auth.authenticated.models.User
import com.uramnoil.serverist.domain.common.user.Id

/**
 * Authenticated User用のリポジトリ
 * findById以外のクエリ系メソッドは、アプリケーション層に直接記述する。
 */
interface UserRepository {
    /**
     * 挿入
     * @param user  新規作成したいユーザ
     * @return      データベース・通信系のエラー
     */
    suspend fun insert(user: User): Result<Unit>

    /**
     * 更新
     * @param user  更新したいユーザ
     * @return      データベース・通信系のエラー
     */
    suspend fun update(user: User): Result<Unit>

    /**
     * 削除
     * @param user  削除したいユーザ
     * @return      データベース・通信系のエラー
     */
    suspend fun delete(user: User): Result<Unit>

    /**
     * Idで検索
     * @param id    目的のユーザのID
     * @return      データベース・通信系のエラー
     */
    suspend fun findById(id: Id): Result<User?>
}