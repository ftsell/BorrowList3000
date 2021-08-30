export type FormValidator = (v: string) => boolean | string;

export interface Alert {
    type?: "success" | "info" | "warning" | "error";
    message: string;
}
