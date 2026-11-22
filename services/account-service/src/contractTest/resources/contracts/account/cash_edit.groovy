package contracts.account

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Cash edit mock'
    name 'cash_edit'

    request {
        method POST()
        url '/money/'
        headers {
            contentType(applicationJson())
            header 'Authorization', value(
                    // Для консьюмера (WireMock): любой Bearer-токен
                    consumer(regex('Bearer\\s+.+')),
                    // Для провайдера (MockMvc-тест): ровно этот токен
                    producer('Bearer test-token')
            )
        }
        body (
                accountId: 'tu1',
                amount: 200,
                action: 'PUT'
        )
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                fullName: 'Денис',
                amount: 200
        )
    }
}