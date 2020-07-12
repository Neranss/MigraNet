from enum import IntEnum


class Roles(IntEnum):
    User = 1
    Moderator = 2
    Admin = 3


class Status(IntEnum):
    Deleted = -2
    Blocked = -1
    Active = 1


class MessageStatus(IntEnum):
    NOT_READ = 0
    READ = 1


class ChatType(IntEnum):
    MULTI_CHAT = 1
    SINGLE_CHAT = 2


USERS_DB = "users"
BLOCKED_BY_USER_DB = "blocked_by_user"
USER_SESSION_DB = "user_sessions"
CHATS_DB = "chats"
CHAT_IDS_DB = "chat_ids"
CHAT_MESSAGES_DB = "chat_messages"
FRIENDS_DB = "friends"
FRIENDS_WAIT_FRIEND_ADD = "wait_friend_add"
ACTIONS_DB = "actions"
ACTION_MEMBERS_DB = "action_members"
GEOLOCATION_DB = "geolocation"

ALLOWED_SEARCH_KEYS = {"first_name", "second_name", "patronymic"}
ALLOWED_DIFFICULT_SEARCH_KEYS = {"birthday"}

COMPARISON_SIGNS = {"e": "==", "ge": ">=", "le": "<=", "g": ">", "l": "<"}
