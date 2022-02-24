package com.adair.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * MMKV kotlin 扩展，使用属性代理的方式
 *
 * @author XuShuai
 * @version v1.0
 * @date 2022/2/11 13:52
 */

/** 基础数据类型代理方法 */
private inline fun <T> MMKV.delegate(
    key: String? = null,
    defaultValue: T,
    crossinline getter: MMKV.(String, T) -> T,
    crossinline setter: MMKV.(String, T) -> Boolean
): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter(key ?: property.name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter(key ?: property.name, value)
    }
}

fun MMKV.boolean(key: String? = null, defaultValue: Boolean = false): ReadWriteProperty<Any, Boolean> =
    delegate(key, defaultValue, MMKV::decodeBool, MMKV::encode)

fun MMKV.int(key: String? = null, defaultValue: Int = 0): ReadWriteProperty<Any, Int> =
    delegate(key, defaultValue, MMKV::decodeInt, MMKV::encode)

fun MMKV.long(key: String? = null, defaultValue: Long = 0L): ReadWriteProperty<Any, Long> =
    delegate(key, defaultValue, MMKV::decodeLong, MMKV::encode)

fun MMKV.float(key: String? = null, defaultValue: Float = 0.0f): ReadWriteProperty<Any, Float> =
    delegate(key, defaultValue, MMKV::decodeFloat, MMKV::encode)

fun MMKV.double(key: String? = null, defaultValue: Double = 0.0): ReadWriteProperty<Any, Double> =
    delegate(key, defaultValue, MMKV::decodeDouble, MMKV::encode)


/** 可以null默认值的代理方法 */
private inline fun <T> MMKV.nullableDefaultValueDelegate(
    key: String? = null,
    defaultValue: T?,
    crossinline getter: MMKV.(String, T?) -> T,
    crossinline setter: MMKV.(String, T?) -> Boolean
): ReadWriteProperty<Any, T?> = object : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return getter(key ?: property.name, defaultValue)
    }


    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        setter(key ?: property.name, value)
    }
}

fun MMKV.byteArray(key: String? = null, defaultValue: ByteArray? = null): ReadWriteProperty<Any, ByteArray?> =
    nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeBytes, MMKV::encode)

fun MMKV.string(key: String? = null, defaultValue: String? = null): ReadWriteProperty<Any, String?> =
    nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeString, MMKV::encode)

fun MMKV.stringSet(key: String? = null, defaultValue: Set<String>? = null): ReadWriteProperty<Any, Set<String>?> =
    nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeStringSet, MMKV::encode)

/** 可以序列表对象代理方法 */
inline fun <reified T : Parcelable> MMKV.parcelable(
    key: String? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T?> = object : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return decodeParcelable(key ?: property.name, T::class.java, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        encode(key ?: property.name, value)
    }
}