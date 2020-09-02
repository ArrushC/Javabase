package com.arrushc.javabase.entities

abstract class Entity(val modifiable: Boolean = true,
                      val readable: Boolean = true,
                      val insertable: Boolean = true)