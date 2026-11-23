package contracts.account

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description 'Trasfer (SUCCESSFUL)'
            name 'cash_transfer'

            request {
                method POST()
                url '/money/transfer'
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
                        fromAccountId: 'tu1',
                        toAccountId: 'tu2',
                        amount: 200
                )
            }

            response {
                status OK()
                headers {
                    contentType(applicationJson())
                }
                body(
                        fullName: 'Денис',
                        amount: 400
                )
            }
        },
        Contract.make {
            description 'Cash edit mock put (UNSUCCESSFUL)'
            name 'cash_transfer_bad_request'

            request {
                method POST()
                url '/money/transfer'
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
                        fromAccountId: 'tu1',
                        toAccountId: 'tu2',
                        amount: 500
                )
            }

            response {
                status BAD_REQUEST()
            }
        },
        Contract.make {
            description 'Cash edit mock put (UNSUCCESSFUL)'
            name 'cash_transfer_bad_request_no_such_account'

            request {
                method POST()
                url '/money/transfer'
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
                        fromAccountId: 'tu1',
                        toAccountId: 'tu3',
                        amount: 100
                )
            }

            response {
                status BAD_REQUEST()
            }
        },
]

