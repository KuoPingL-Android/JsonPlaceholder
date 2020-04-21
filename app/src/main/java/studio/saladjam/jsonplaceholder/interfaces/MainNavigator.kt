package studio.saladjam.jsonplaceholder.interfaces

import studio.saladjam.jsonplaceholder.enums.Pages

interface MainNavigator {
    fun navigateTo(page: Pages)
}