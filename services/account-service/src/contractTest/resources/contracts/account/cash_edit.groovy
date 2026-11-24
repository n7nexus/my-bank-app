package contracts.account

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description 'Cash edit mock put (SUCCESSFUL)'
            name 'cash_edit_put'

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
                        amount: 400
                )
            }
        },
        Contract.make {
            description 'Cash edit mock put (UNSUCCESSFUL)'
            name 'cash_edit_put_no_such_acc'

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
                        accountId: 'tu3',
                        amount: 200,
                        action: 'PUT'
                )
            }

            response {
                status BAD_REQUEST()
            }
        },
        Contract.make {
            description 'Cash edit mock get (SUCCESSFUL)'
            name 'cash_edit_get'

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
                        action: 'GET'
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
            description 'Cash edit mock get (UNSUCCESSFUL)'
            name 'cash_edit_get_no_such_acc'

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
                        accountId: 'tu3',
                        amount: 200,
                        action: 'GET'
                )
            }

            response {
                status BAD_REQUEST()
            }
        },
        Contract.make {
            description 'Cash edit mock get (UNSUCCESSFUL)'
            name 'cash_edit_get_not_enough_money'

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
                        amount: 500,
                        action: 'GET'
                )
            }

            response {
                status BAD_REQUEST()
            }
        },
]