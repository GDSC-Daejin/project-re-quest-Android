package com.example.a2024solutionchallenge

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a2024solutionchallenge.data.LoginGoogleResponse
import com.example.a2024solutionchallenge.data.LoginResponsesData
import com.example.a2024solutionchallenge.data.TokenRequest
import com.example.a2024solutionchallenge.databinding.ActivityLogin2Binding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Response
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val mBinding by lazy {
        ActivityLogin2Binding.inflate(layoutInflater)
    }
    private lateinit var currentUser : LoginGoogleResponse
    //받은 계정 정보
    private var userEmail = ""
    //데이터 받아오기 준비
    private var isReady = false

    private val CLIENT_WEB_ID_KEY = BuildConfig.client_web_id_key
    private val CLIENT_WEB_SECRET_KEY = BuildConfig.client_web_secret_key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        setResultSignUp()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            //.requestIdToken(R.string.defalut_client_id.toString())
            .requestServerAuthCode(CLIENT_WEB_ID_KEY)
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mBinding.signInButton.setOnClickListener {
            signIn()
        }
    }


    private fun signInCheck(userInfoToken : TokenRequest) {
        println("signInCheck")
        /*var interceptor = HttpLoggingInterceptor()
        interceptor = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()*/

        /*val retrofit = Retrofit.Builder().baseUrl("url 주소")
            .addConverterFactory(GsonConverterFactory.create())
            //.client(client) 이걸 통해 통신 오류 log찍기 가능
            .build()
        val service = retrofit.create(MioInterface::class.java)*/
        val call = RetrofitServerConnect.service
        val saveSharedPreferenceGoogleLogin = SaveSharedPreferenceGoogleLogin()

        CoroutineScope(Dispatchers.IO).launch {
            call.addUserInfoData(userInfoToken).enqueue(object : retrofit2.Callback<LoginResponsesData> {
                override fun onResponse(
                    call: retrofit2.Call<LoginResponsesData>,
                    response: retrofit2.Response<LoginResponsesData?>
                ) {
                    if (response.isSuccessful) {
                        val builder =  OkHttpClient.Builder()
                            .connectTimeout(1, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .addInterceptor(HeaderInterceptor(response.body()!!.accessToken))
                        intent.apply {
                            putExtra("accessToken", saveSharedPreferenceGoogleLogin.setToken(this@LoginActivity, response.body()!!.accessToken).toString())
                            putExtra("expireDate", saveSharedPreferenceGoogleLogin.setExpireDate(this@LoginActivity, response.body()!!.accessTokenExpiresIn.toString()).toString())
                        }
                        builder.build()
                        println(response.body()!!.accessTokenExpiresIn.toString())


                    } else {
                        println("fail")
                    }
                }
                override fun onFailure(call: retrofit2.Call<LoginResponsesData>, t: Throwable) {
                    println("실패" + t.message.toString())
                }
            })
        }

        /*val s = service.addUserInfoData(userInfoToken).execute().code()
        println(s)*/
    }

    private fun setResultSignUp() {
        println("setResultSignUp")
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)

                if (userEmail.substring(9..20).toString() == "daejin.ac.kr") {
                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    this@LoginActivity.finish()
                } else {
                    Toast.makeText(this, "대진대학교 학교 계정으로 로그인해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun handleSignInResult(completedTask : Task<GoogleSignInAccount>) {
        try {
            println("handleSignInResult")
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val authCode = account.serverAuthCode
            val saveSharedPreferenceGoogleLogin = SaveSharedPreferenceGoogleLogin()

            userEmail = email

            //회원가입과 함께 새로운 계정 정보 저장
            if (saveSharedPreferenceGoogleLogin.getUserEMAIL(this@LoginActivity)!!.isEmpty()) {
                intent.apply {
                    putExtra("email", saveSharedPreferenceGoogleLogin.setUserEMAIL(this@LoginActivity, email).toString())
                }

                startActivity(intent)
                finish()

            } else { //현재 로그인, 또는 로그인했던 정보가 저장되어있으면 home으로
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                this@LoginActivity.finish()
            }

            Toast.makeText(this, "tjd", Toast.LENGTH_SHORT).show()
            println(email)
            println(authCode.toString())

            getAccessToken(authCode!!)



        } catch (e : ApiException) {
            Log.w("failed", "signinresultfalied code = " + e.statusCode)
        }
    }


    private fun getAccessToken(authCode : String) {
        println("getAccessToken")
        val client = OkHttpClient()
        val requestBody: RequestBody = FormBody.Builder()
            //1시간
            .add("grant_type", "authorization_code")
            .add(
                "client_id",
                CLIENT_WEB_ID_KEY
            )
            .add("client_secret", CLIENT_WEB_SECRET_KEY)
            .add("redirect_uri", "")
            .add("code", authCode)
            //refresh token 필요시
            .add("response_type", "code")
            .add("access_type", "offline")
            .add("approval_prompt", "force")
            .build()

        val request = Request.Builder()
            .url("https://oauth2.googleapis.com/token")
            .post(requestBody)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    print("Failed")
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val jsonObject = JSONObject(response.body!!.string())
                        val message = jsonObject.keys() //.toString(5)

                        //json파일 키와 벨류를 잠시담는 변수
                        val tempKey = ArrayList<String>()
                        val tempValue = ArrayList<String>()
                        //정리한번
                        user_info.clear()

                        while (message.hasNext()) {
                            val s = message.next().toString()
                            tempKey.add(s)

                        }

                        for (i in tempKey.indices) {
                            //fruitValueList.add(fruitObject.getString(fruitKeyList.get(j)));
                            tempValue.add(jsonObject.getString(tempKey[i]))
                            println(tempKey[i] + "/" + jsonObject.getString(tempKey[i]))
                        }

                        user_info.add(LoginGoogleResponse(tempValue[0], tempValue[1].toInt(), tempValue[2], tempValue[3], tempValue[4]))
                        currentUser = user_info[0]
                        println(message)
                        println(user_info[0].id_token)
                        //createClipData(user_info[0].id_token)
                        signInCheck(TokenRequest(currentUser.id_token))
                        tempKey.clear()
                        tempValue.clear()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
        }
    }
    private fun signIn() {
        println("signIn")
        val signIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signIntent)
    }

    private fun refreshAccessToken(refreshToken: String) {
        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("grant_type", "refresh_token")
            .add("refresh_token", refreshToken)
            .add("client_id", CLIENT_WEB_ID_KEY)
            .add("client_secret", CLIENT_WEB_SECRET_KEY)
            .build()

        val request = Request.Builder()
            .url("https://oauth2.googleapis.com/token")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 실패 시 처리
                Log.e("Refresh Token", "Failed to refresh access token")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonResponse = JSONObject(response.body!!.string())
                    val newAccessToken = jsonResponse.getString("access_token")

                    // TODO: 새로운 액세스 토큰을 저장하고 사용
                    // 여기에서 새로운 액세스 토큰을 저장하고 필요한 요청에 사용합니다.
                    println(newAccessToken)
                } else {
                    // 실패 시 처리
                    Log.e("Refresh Token", "Failed to refresh access token")
                }
            }
        })
    }
}