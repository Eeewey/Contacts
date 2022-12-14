package com.example.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "conts")
class ContEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var name: String = ""
    var lastName: String = ""
    var dateOfBirth = ""
    var number = ""

    var IsActive = 1
}