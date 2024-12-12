package com.rentify.user.app.view.userScreens.SurroundingRoomsScreen

import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.rentify.user.app.model.FakeModel.MarkerInfo
import com.rentify.user.app.model.Landlord
import com.rentify.user.app.model.Model.BuildingId
import com.rentify.user.app.model.Model.RoomResponse

class FakeMarker {
    val fakeMarker = listOf(
        MarkerInfo(
            point = Point.fromLngLat(48.200839, 16.358124),
            room = RoomResponse(
                _id = "6753d8a1fb1a97de5f413c81",
                amenities = listOf("Vệ sinh khép kín", "Ra vào vân tay", "Không chung chủ"),
                building_id = BuildingId(
                    _id = "6752eb207f9f19f2287915d1",
                    nameBuilding = "HL1",
                    address = "68, Tran Dang Ninh, Phường Dịch Vọng, Cầu Giấy, Hà Nội",
                    description = "Tòa nhà mới, đẹp"
                ),
                created_at = "2024-12-07T05:09:53.917Z",
                description = "Phòng mới",
                limit_person = 4,
                photos_room = listOf(
                    "public\\imageRooms\\photos_room-1733553872531z6096254938860_8c5589515e5bc6c8a7aeb7ff1721050b.jpg",
                    "public\\imageRooms\\photos_room-1733553872533z6096254944065_420e98a24c344f4767f808ed4f919cd9.jpg",
                    "public\\imageRooms\\photos_room-1733553872533z6096254946139_da785d7d464d56b10f3a3ca0960ea99f.jpg",
                    "public\\imageRooms\\photos_room-1733553872537z6096254954205_d2f8ebc4c5644af83be8f543cb3e2124.jpg"
                ),
                price = 4000000,
                room_name = "P102",
                room_type = "single",
                service = listOf("Giường", "Điều hòa"),
                size = "35",
                status = 0,
                updated_at = "2024-12-07T06:44:32.579Z",
                video_room = emptyList()
            )
        ),
        MarkerInfo(
//            point = Point.fromLngLat(-122.0765, 37.3984),
            point = Point.fromLngLat(105.800688,21.063264),
            room = RoomResponse(
                _id = "67548be0fa8c2e626a79e035",
                amenities = listOf("Gác xép", "Ra vào vân tay"),
                building_id = BuildingId(
                    _id = "6752eb207f9f19f2287915d1",
                    nameBuilding = "HL1",
                    address = "68, Tran Dang Ninh, Phường Dịch Vọng, Cầu Giấy, Hà Nội",
                    description = "Tòa nhà mới, đẹp"
                ),
                created_at = "2024-12-07T17:54:40.579Z",
                description = "Phòng mới đẹp",
                limit_person = 5,
                photos_room = listOf(
                    "public\\imageRooms\\photos_room-1733594080556z6096254946139_da785d7d464d56b10f3a3ca0960ea99f.jpg",
                    "public\\imageRooms\\photos_room-1733594080561z6096254954205_d2f8ebc4c5644af83be8f543cb3e2124.jpg"
                ),
                price = 3900000,
                room_name = "P201",
                room_type = "single",
                service = listOf("Tủ lạnh", "Điều hòa"),
                size = "40",
                status = 0,
                updated_at = "2024-12-07T17:54:40.579Z",
                video_room = emptyList()
            )
        )
    )

    //list style map
    val mapStyles = listOf(
        Style.STANDARD,
        Style.STANDARD_SATELLITE,
        Style.MAPBOX_STREETS,
        Style.OUTDOORS,
        Style.SATELLITE_STREETS,
    )

}