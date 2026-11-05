rootProject.name = "my-bank-app"

extra["springCloudVersion"] = "2025.1.4"

include(
    "webui",
    "gateway",
    "accounts-service",
    "cash-service",
    "transfer-service",
    "notifications-service"
)