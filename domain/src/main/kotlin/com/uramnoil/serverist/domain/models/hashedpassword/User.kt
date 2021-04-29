package com.uramnoil.serverist.domain.models.hashedpassword

import com.uramnoil.serverist.domain.models.kernel.user.HashedPassword
import com.uramnoil.serverist.domain.models.kernel.user.Id

class User(val id: Id, val hashedPassword: HashedPassword)