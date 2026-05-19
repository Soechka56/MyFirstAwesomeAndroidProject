package com.example.network

import com.example.network.pojo.BattleLogResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

// it's not a good implementation of api, because it server-side, need white ip
interface BrawlStarsApi {

    // player tag starts with '#' and should be encoded as '%23'
    @GET("players/%23{playerTag}/battlelog")
    suspend fun getBattleLog(
        @Path(
            "playerTag", //encoded = true
        ) playerTag: String
    ): BattleLogResponseDto // api requires object, not a list
}

