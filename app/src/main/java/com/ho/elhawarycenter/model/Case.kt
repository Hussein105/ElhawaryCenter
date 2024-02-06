package com.ho.elhawarycenter.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "cases")
data class Case(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "diagnosis") val diagnosis: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "paid") val paid: Float,
    @ColumnInfo(name = "remaining") val remaining: Float,
    @ColumnInfo(name = "sessions") val sessions: Int,
    @ColumnInfo(name = "tSessions") val tSessions: Int,
    @ColumnInfo(name = "rSessions") val rSessions: Int,
    @ColumnInfo(name = "notes") val notes: String,
    @ColumnInfo(name = "attachmentUri") val attachmentUri: String,
    @ColumnInfo(name = "admissionDate") val admissionDate: Long
) : Parcelable
