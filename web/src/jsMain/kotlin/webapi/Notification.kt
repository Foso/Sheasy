package webapi

// Available options
// See https://developer.mozilla.org/en-US/docs/Web/API/Notification/Notification

interface NotificationOptions {
    var tag: String?
    var icon: String?
    var body: String?
    var title: String?


}