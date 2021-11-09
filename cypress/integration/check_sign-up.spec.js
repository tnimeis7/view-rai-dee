const url = 'http://localhost:8080'

describe('Sign up', () => {
    it('Go to url', () => {
        cy.visit(url)
    })

    it('Go Login Page', () => {
        cy.get('[class="btn btn-outline-light"]')
            .contains('Login')
            .click()
    })

    it('Sign-up', () => {
        cy.get('[id="toSignup"]').click()
        cy.get('[id="usernameInput"]')
            .type('Mintkakx2')
            .click()
        cy.get('[id="emailInput"]')
            .type('kak.2@gmail.com')
            .click()
        cy.get('[id="passwordInput"]')
            .type('hak')
            .click()
        cy.get('[id="confirmPasswordInput"]')
            .type('hak')
            .click()
        cy.get('[class="btn button btn-login button--100"]')
            .contains('สมัครบัญชี')
            .click()
    })


})
