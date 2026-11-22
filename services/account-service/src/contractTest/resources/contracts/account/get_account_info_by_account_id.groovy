package contracts.account

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'Get Account info of account tu1'
    name 'get_account_info_by_account_id'

    request {
        method GET()
        url '/tu1'
        headers {
            header 'Authorization', value(
                    // Для консьюмера (WireMock): любой Bearer-токен
                    consumer(regex('Bearer\\s+.+')),
                    // Для провайдера (MockMvc-тест): ровно этот токен
                    producer('Bearer test-token')
            )
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(
                accountId: 'tu1',
                fullName: 'Денис',
                birthdate: '1999-11-12',
                money: '200'
        )
    }
}