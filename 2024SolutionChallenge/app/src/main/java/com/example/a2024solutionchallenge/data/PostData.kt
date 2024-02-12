package com.example.a2024solutionchallenge.data

data class PostData(
    var accountID : String?,
    var postID : Int?, //position
    var postContent : String?,
    var postContentImage : String?, //uri
    //프로필이미지? user자체?
    var postTargetDate : String?,
    var postTargetTime : String?,
    var postFavoriteCnt : Int?,
    ) : java.io.Serializable {

}
