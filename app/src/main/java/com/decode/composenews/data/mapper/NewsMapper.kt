package com.decode.composenews.data.mapper

import com.decode.composenews.data.local.entity.NewsEntity
import com.decode.composenews.data.remote.dto.NewsResponse
import com.decode.composenews.domain.model.News
import com.decode.composenews.util.formatDate

fun NewsResponse.toNewsEntity(): NewsEntity{
    return NewsEntity(
        id = id,
        author = author,
        category = category,
        description = description,
        image = image,
        published = published.formatDate(),
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
        published = published.formatDate(),
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
        published = published,
        title = title,
        url = url,
    )
}