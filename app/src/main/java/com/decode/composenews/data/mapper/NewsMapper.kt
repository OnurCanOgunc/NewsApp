package com.decode.composenews.data.mapper

import com.decode.composenews.data.local.entity.NewsEntity
import com.decode.composenews.data.remote.dto.NewsResponse
import com.decode.composenews.domain.model.News
import com.decode.composenews.util.cleanDate

fun NewsResponse.toNewsEntity(): NewsEntity{
    return NewsEntity(
        id = id,
        author = author,
        category = category,
        description = description,
        image = image,
        published = published.cleanDate(),
        title = title,
        url = url,
    )
}

fun NewsResponse.toNews(): News{
    return News(
        id = id,
        author = author,
        category = category,
        description = description,
        image = image,
        published = published.cleanDate(),
        title = title,
        url = url,
    )
}

fun NewsEntity.toNews(): News{
    return News(
        id = id,
        author = author,
        category = category,
        description = description,
        image = image,
        published = published.cleanDate(),
        title = title,
        url = url,
    )
}