package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }
    }

    @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES') " +
                "or @algaSecurity.gerenciaRestaurante(#restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarFuncionamento {
        }
    }

    @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == returnObject.cliente.id or "
                + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == #filtro.clienteId or"
                + "@algaSecurity.gerenciaRestaurante(#filtro.restauranteId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodePesquisar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @interface PodeCriar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or @algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
        @interface PodeGerenciarPedidos {
        }
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }
    }

    @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }
    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }
    }

    @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.getUsuarioId() == #usuarioId")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarPropriaSenha {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or" +
                " @algaSecurity.getUsuarioId() == #usuarioId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarUsuario {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }
    }
}
