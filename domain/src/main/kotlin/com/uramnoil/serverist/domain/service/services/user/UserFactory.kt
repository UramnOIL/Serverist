package com.uramnoil.serverist.domain.service.services.user

import com.uramnoil.serverist.domain.service.models.user.Description
import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.Name
import com.uramnoil.serverist.domain.service.models.user.User
import java.util.*

interface UserFactory {
    fun create(id: UUID, name: String, description: String) = User(Id(id), Name(name), Description(description))

}