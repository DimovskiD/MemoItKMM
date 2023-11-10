package com.memoit.ddimovski.memoit.domain.note

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.time.DateTimeUtil
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val description: String = "" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque dapibus tristique ex, vitae dignissim ante pulvinar et. Proin efficitur et tellus a tincidunt. Cras arcu justo, sodales ut rutrum vitae, imperdiet sed purus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nulla mollis non neque eget lobortis. Praesent sit amet ultrices velit, id iaculis justo. Nulla eget dignissim tortor. Etiam eget lorem vel tellus pretium suscipit. Pellentesque blandit tortor a enim condimentum, quis mollis enim mollis. In leo elit, volutpat non finibus vel, dictum sed quam. Pellentesque tincidunt semper nibh, sodales sollicitudin augue posuere et.\n" +
            "\n" +
            "In quis suscipit libero, quis efficitur libero. Phasellus faucibus non urna eu finibus. Praesent imperdiet orci pharetra, tincidunt enim id, venenatis felis. Ut maximus tristique neque quis semper. Maecenas mattis et lectus eu consequat. Donec tempus elementum congue. Praesent dolor ligula, tincidunt sed ullamcorper ac, pretium vitae nulla. Vestibulum bibendum, turpis in mattis cursus, turpis nisi mattis turpis, nec pulvinar ante nibh ut diam. Nam viverra neque eget venenatis placerat. Donec sed ultrices ipsum, vitae efficitur ligula. Curabitur sed tellus faucibus, posuere lorem consequat, mattis sapien. Vivamus a massa non nibh pharetra consequat in at tellus. Nam maximus et libero fringilla pharetra. Etiam lobortis sem ac tortor rhoncus, sit amet convallis urna placerat. Ut condimentum feugiat metus, eu condimentum ex volutpat nec.\n" +
            "\n" +
            "Sed eget odio efficitur, condimentum purus et, dapibus ipsum. Pellentesque eu nulla ante. Maecenas iaculis in orci eu commodo. Maecenas tincidunt dolor odio, non fringilla sapien congue quis. Quisque commodo lectus nisi, sed pulvinar erat egestas a. In sed purus vitae erat auctor facilisis ac a nibh. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Phasellus congue velit sem, vel laoreet turpis placerat in. Duis ut tellus neque. In quis ligula neque. Phasellus at felis ut lacus rutrum congue non sit amet dui. Mauris ultrices, ex at convallis posuere, lectus justo rutrum dolor, id vehicula felis libero ut ipsum.\n" +
            "\n",
    val categories: List<Category> = emptyList(),
    val dueDate: LocalDateTime? = null,
    val notifications: Boolean = false,
    val isCompleted: Boolean = false,
    val created: LocalDateTime = DateTimeUtil.now()
)
