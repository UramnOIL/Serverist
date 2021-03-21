package com.uramnoil.serverist.domain.service.models.user

import com.uramnoil.serverist.domain.service.models.user.Description
import com.uramnoil.serverist.domain.service.models.user.Id
import com.uramnoil.serverist.domain.service.models.user.Name

class User(val id: Id, val name: Name, val description: Description) {
}