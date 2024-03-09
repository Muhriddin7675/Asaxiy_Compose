package com.example.asaxiycompose2.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.asaxiycompose2.R
import com.example.asaxiycompose2.ui.theme.AsaxiyCompose2Theme

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<ProfileViewModel>()
        ProfileContent(viewModel)
    }

    @Composable
    fun ProfileContent(viewModel: ProfileViewModel) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Text(
                text = "Profil", fontSize = 24.sp, color = Color.Black, fontFamily = FontFamily(
                    Font(
                        R.font.nunito_extrabold
                    )
                ), modifier = Modifier.padding(start = 14.dp, top = 14.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 14.dp)
            ) {


                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(60.dp)
                        .clip(shape = CircleShape),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Valiyev Muhriddin",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.nunito_semi_bold)),
                        fontSize = 22.sp,
                        modifier = Modifier.padding(start = 20.dp)
                    )

                    Text(
                        text = "Tahrirlash uchun ustiga bosing!",
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        modifier = Modifier.padding(start = 20.dp, top = 2.dp)

                    )
                }


            }

//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(64.dp)
//                .clip(RoundedCornerShape(10.dp))
//                .padding(top = 20.dp, start = 15.dp, end = 15.dp),
//            colors = ButtonDefaults.buttonColors(Color(0xFF008dff)),
//
//        ) {
//            Text(text = "Hisobni to'ldirish")
//        }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFE9E9E9))
                    .height(40.dp)
            ) {
                Text(
                    text = "Hisob raqam: A415154",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(36.dp)
                        .padding(6.dp)
                        .align(Alignment.CenterEnd)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .height(56.dp)
                    .background(Color(0xFF008dff))

            ) {
                Text(
                    text = "Hisobni to'ldirish",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .height(1.dp)
                    .background(Color(0xFFE9E9E9))
            )

            ItemProfile(0xFFE9E9E9, R.drawable.ic_users, "Sevimli avtorlar",viewModel::onEventDispatcherProfil )
            ItemProfile(0xFFE9E9E9, R.drawable.ic_cart, "Buyurtmalar tarixi",viewModel::onEventDispatcherProfil)
            ItemProfile(0xFFE9E9E9, R.drawable.ic_notification_profile, "Xabarnomalar",viewModel::onEventDispatcherProfil)
            ItemProfile(0xFFE9E9E9, R.drawable.ic_language, "Ilova tili",viewModel::onEventDispatcherProfil)
            ItemProfile(0xFFE9E9E9, R.drawable.ic_night_mode, "Ilova mavzusi",viewModel::onEventDispatcherProfil)
            ItemProfile(0xFFE9E9E9, R.drawable.ic_about, "Biz xaqimida",viewModel::onEventDispatcherProfil)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .height(1.dp)
                    .background(Color(0xFFE9E9E9))
            )

            ItemProfile(0xFF1EE91E63, R.drawable.ic_log_out, "Akkountdan chiqish",viewModel::onEventDispatcherProfil) // 0xFF1EE91E63


        }
    }

    @Composable
    fun ItemProfile(bgColor: Long, icon: Int, text: String,onEventDispatcher:(ProfileIntent) -> Unit) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
            ).clickable {
                if(text == "Buyurtmalar tarixi"){
                    onEventDispatcher.invoke(ProfileIntent.ClickOrder)
                }
            }
            .height(56.dp),
            verticalAlignment = Alignment.CenterVertically


        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(bgColor))
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                )
            }

            Text(
                text = text,
                color = Color.Black,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 15.dp),
            )

        }
    }


    @Preview
    @Composable
    private fun PreviewContent() {
        AsaxiyCompose2Theme {
//            ProfileContent({})
        }
    }
}

