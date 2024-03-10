package com.example.asaxiycompose2.screen.profile

import com.example.asaxiycompose2.screen.orderhestore.OrderIntent

interface ProfileIntent {
    data object ClickOrder:ProfileIntent
    data object ClickLogAut : ProfileIntent
}