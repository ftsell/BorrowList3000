/* tslint:disable */
/* eslint-disable */
/**
 * Thing-People-List Api
 * Public API of the Thing-People-List Service. Thing-People-List is a Web-Service to keep track of which people did what i.e. who borrowed what from you.
 *
 * The version of the OpenAPI document: 2.0.0
 * Contact: dev@finn-thorben.me
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import * as runtime from "../runtime";
import type {
  LoginRequest,
  LoginResponse,
  Problem,
  RegisterRequest,
  SessionDto,
} from "../models";

import {
  LoginRequestFromJSON,
  LoginRequestToJSON,
  LoginResponseFromJSON,
  LoginResponseToJSON,
  ProblemFromJSON,
  ProblemToJSON,
  RegisterRequestFromJSON,
  RegisterRequestToJSON,
  SessionDtoFromJSON,
  SessionDtoToJSON,
} from "../models";

export interface LoginOperationRequest {
  loginRequest: LoginRequest;
}

export interface LogoutAllSessionRequest {
  includingCurrent?: boolean;
}

export interface LogoutSessionRequest {
  sessionId: string;
}

export interface RegisterOperationRequest {
  registerRequest: RegisterRequest;
}

/**
 *
 */
export class AuthControllerApi extends runtime.BaseAPI {
  /**
   * List all active sessions
   */
  async listSessionsRaw(
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<Array<SessionDto>>> {
    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/auth/sessions`,
        method: "GET",
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      jsonValue.map(SessionDtoFromJSON)
    );
  }

  /**
   * List all active sessions
   */
  async listSessions(initOverrides?: RequestInit): Promise<Array<SessionDto>> {
    const response = await this.listSessionsRaw(initOverrides);
    return await response.value();
  }

  /**
   * Login to an existing user account
   */
  async loginRaw(
    requestParameters: LoginOperationRequest,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<LoginResponse>> {
    if (
      requestParameters.loginRequest === null ||
      requestParameters.loginRequest === undefined
    ) {
      throw new runtime.RequiredError(
        "loginRequest",
        "Required parameter requestParameters.loginRequest was null or undefined when calling login."
      );
    }

    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    headerParameters["Content-Type"] = "application/json";

    const response = await this.request(
      {
        path: `/api/auth/login`,
        method: "POST",
        headers: headerParameters,
        query: queryParameters,
        body: LoginRequestToJSON(requestParameters.loginRequest),
      },
      initOverrides
    );

    return new runtime.JSONApiResponse(response, (jsonValue) =>
      LoginResponseFromJSON(jsonValue)
    );
  }

  /**
   * Login to an existing user account
   */
  async login(
    requestParameters: LoginOperationRequest,
    initOverrides?: RequestInit
  ): Promise<LoginResponse> {
    const response = await this.loginRaw(requestParameters, initOverrides);
    return await response.value();
  }

  /**
   * Logout from all active sessions
   */
  async logoutAllSessionRaw(
    requestParameters: LogoutAllSessionRequest,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<void>> {
    const queryParameters: any = {};

    if (requestParameters.includingCurrent !== undefined) {
      queryParameters["includingCurrent"] = requestParameters.includingCurrent;
    }

    const headerParameters: runtime.HTTPHeaders = {};

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/auth/sessions`,
        method: "DELETE",
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    );

    return new runtime.VoidApiResponse(response);
  }

  /**
   * Logout from all active sessions
   */
  async logoutAllSession(
    requestParameters: LogoutAllSessionRequest = {},
    initOverrides?: RequestInit
  ): Promise<void> {
    await this.logoutAllSessionRaw(requestParameters, initOverrides);
  }

  /**
   * Logout the given session
   */
  async logoutSessionRaw(
    requestParameters: LogoutSessionRequest,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<void>> {
    if (
      requestParameters.sessionId === null ||
      requestParameters.sessionId === undefined
    ) {
      throw new runtime.RequiredError(
        "sessionId",
        "Required parameter requestParameters.sessionId was null or undefined when calling logoutSession."
      );
    }

    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    if (this.configuration && this.configuration.apiKey) {
      headerParameters["Authorization"] =
        this.configuration.apiKey("Authorization"); // token authentication
    }

    const response = await this.request(
      {
        path: `/api/auth/sessions/{sessionId}`.replace(
          `{${"sessionId"}}`,
          encodeURIComponent(String(requestParameters.sessionId))
        ),
        method: "DELETE",
        headers: headerParameters,
        query: queryParameters,
      },
      initOverrides
    );

    return new runtime.VoidApiResponse(response);
  }

  /**
   * Logout the given session
   */
  async logoutSession(
    requestParameters: LogoutSessionRequest,
    initOverrides?: RequestInit
  ): Promise<void> {
    await this.logoutSessionRaw(requestParameters, initOverrides);
  }

  /**
   * Register a new user account
   */
  async registerRaw(
    requestParameters: RegisterOperationRequest,
    initOverrides?: RequestInit
  ): Promise<runtime.ApiResponse<void>> {
    if (
      requestParameters.registerRequest === null ||
      requestParameters.registerRequest === undefined
    ) {
      throw new runtime.RequiredError(
        "registerRequest",
        "Required parameter requestParameters.registerRequest was null or undefined when calling register."
      );
    }

    const queryParameters: any = {};

    const headerParameters: runtime.HTTPHeaders = {};

    headerParameters["Content-Type"] = "application/json";

    const response = await this.request(
      {
        path: `/api/auth/register`,
        method: "POST",
        headers: headerParameters,
        query: queryParameters,
        body: RegisterRequestToJSON(requestParameters.registerRequest),
      },
      initOverrides
    );

    return new runtime.VoidApiResponse(response);
  }

  /**
   * Register a new user account
   */
  async register(
    requestParameters: RegisterOperationRequest,
    initOverrides?: RequestInit
  ): Promise<void> {
    await this.registerRaw(requestParameters, initOverrides);
  }
}
