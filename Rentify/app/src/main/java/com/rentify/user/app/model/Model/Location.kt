package com.rentify.user.app.model.Model

data class Location(
    val province: Province,
    val district: District?,
    val ward: Ward?
)

data class Province(
    val code: String,
    val name: String,
    val division_type: String,
    val codename: String,
    val phone_code: Int,
    val districts: List<District>? = null
)
data class District(
    val code: String,
    val name: String,
    val division_type: String,
    val codename: String,
    val province_code: String,
    val wards: List<Ward>? = null
)

data class Ward(
    val code: String,
    val name: String,
    val codename: String,
    val district_code: String
)