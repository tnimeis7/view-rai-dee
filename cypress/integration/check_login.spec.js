const url = 'http://localhost:8080'

describe('Login', () => {
    it('Go to url', () => {
        cy.visit(url)
    })

    it('Go Login Page', () => {
        cy.get('[class="btn btn-primary"]')
            .contains('Login')
            .click()
    })

    it('Login', () => {
        cy.get('[id="usernameInput"]')
            .type('tnime07x')
            .click()
        cy.get('[id="passwordInput"]')
            .type('a')
            .click()
        cy.get('[class="btn button btn-login button--100"]')
            .contains('เข้าสู่ระบบ')
            .click()
    })

})