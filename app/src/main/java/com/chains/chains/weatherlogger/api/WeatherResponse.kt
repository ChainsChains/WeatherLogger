package com.chains.chains.weatherlogger.api

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @field:SerializedName("base")
    val base: String,
    @field:SerializedName("clouds")
    val clouds: Clouds,
    @field:SerializedName("cod")
    val cod: Int,
    @field:SerializedName("coord")
    val coord: Coord,
    @field:SerializedName("dt")
    val dt: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("main")
    val main: Main,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("sys")
    val sys: Sys,
    @field:SerializedName("timezone")
    val timezone: Int,
    @field:SerializedName("visibility")
    val visibility: Int,
    @field:SerializedName("weather")
    val weather: List<Weather>,
    @field:SerializedName("wind")
    val wind: Wind
) {


    data class Clouds(
        @field:SerializedName("all")
        val all: Int
    )

    data class Coord(
        @field:SerializedName("lat")
        val lat: Double,
        @field:SerializedName("lon")
        val lon: Double
    )

    data class Main(
        @field:SerializedName("humidity")
        val humidity: Int,
        @field:SerializedName("pressure")
        val pressure: Int,
        @field:SerializedName("temp")
        val temp: Double,
        @field:SerializedName("temp_max")
        val tempMax: Double,
        @field:SerializedName("temp_min")
        val tempMin: Int
    )

    data class Sys(
        @field:SerializedName("country")
        val country: String,
        @field:SerializedName("id")
        val id: Int,
        @field:SerializedName("sunrise")
        val sunrise: Int,
        @field:SerializedName("sunset")
        val sunset: Int,
        @field:SerializedName("type")
        val type: Int
    )

    data class Weather(
        @field:SerializedName("description")
        val description: String,
        @field:SerializedName("icon")
        val icon: String,
        @field:SerializedName("id")
        val id: Int,
        @field:SerializedName("main")
        val main: String
    )

    data class Wind(
        @field:SerializedName("deg")
        val deg: Int,
        @field:SerializedName("speed")
        val speed: Double
    )

}