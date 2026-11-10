rootProject.name = "my-bank-app"

include(
    "webui",
    "gateway",
    "services:account-service",
    "services:cash-service",
    "services:transfer-service",
    "services:notification-service"
)